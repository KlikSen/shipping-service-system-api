package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.*;
import com.example.shippingservicesystemapi.entity.User;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.service.*;
import com.example.shippingservicesystemapi.template.EmailTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserMapper userMapper; //needed for converting from registerDTO to userDTO
    private ConfirmationService confirmationService;
    private UserService userService;
    private EmailSenderService emailSenderService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegisterDTO registerDTO) {
        final UserDTO userDTO = userService.create(userMapper.toDTO(registerDTO));

        //generating a 15-minute token
        ConfirmationDTO confirmationDTO = confirmationService.create(userDTO);

        //sending an email
        final String link = "http://localhost:8080/confirm?token=".concat(confirmationDTO.getToken()); //the link is temporary
        final String content = EmailTemplate.contentForEmailConfirmation(userDTO.getFirstName(), link);
        emailSenderService.send(userDTO.getEmail(), content);
    }

    @Override
    public JwtDTO login(final LoginDTO loginDTO) {
        final UserDetails userDetails = userMapper.toEntity(userService.readByEmail(loginDTO.getEmail()));

        if (Objects.nonNull(userDetails) &&
                passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())) {
            UserDTO userDTO = userMapper.toDTO((User) userDetails);
            if (!userDetails.isEnabled()) {
                ConfirmationDTO confirmationDTO = confirmationService.create(userDTO);

                final String link = "http://localhost:8080/confirm?token=".concat(confirmationDTO.getToken()); //the link is temporary
                final String content = EmailTemplate.contentForEmailConfirmation(userDTO.getFirstName(), link);
                emailSenderService.send(userDTO.getEmail(), content);

                throw new IllegalStateException("Confirmation of email required!");
            }
            String token = jwtService.generateJwt(userDTO);
            return new JwtDTO(token);
        }
        throw new IllegalStateException("Error has occurred during login process");
    }

    @Override
    @Transactional
    public void logout(final HttpServletRequest httpServletRequest) {
        String jwt = jwtService.getJwtFromRequest(httpServletRequest);
        if (Objects.nonNull(jwt)) {
            jwtService.deleteJwt(jwt);
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return;
        }
        throw new IllegalStateException("Token required");
    }

}
