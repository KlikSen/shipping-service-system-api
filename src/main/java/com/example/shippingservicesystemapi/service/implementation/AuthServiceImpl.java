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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private UserMapper userMapper; //needed for converting from registerDTO to userDTO
    private ConfirmationService confirmationService;
    private UserService userService;
    private EmailSenderService emailSenderService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private final String emailConfirmationURL = "http://localhost:8080/confirm?token=";

    @Override
    public JwtDTO login(final LoginDTO loginDTO) {
        final User user = userMapper.toEntity(userService.readByEmail(loginDTO.getEmail()));

        if (Objects.nonNull(user) &&
                passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            if (!user.isEnabled()) {
                ConfirmationDTO confirmationDTO = confirmationService.create(user);

                final String link = emailConfirmationURL.concat(confirmationDTO.getToken()); //the link is temporary
                final String content = EmailTemplate.contentForEmailConfirmation(user.getFirstName(), link);
                emailSenderService.send(user.getEmail(), content);

                throw new IllegalStateException("Confirmation of email required!");
            }
            String token = jwtService.generateJwt(user);
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
