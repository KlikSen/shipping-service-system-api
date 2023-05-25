package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.*;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.security.JwtService;
import com.example.shippingservicesystemapi.service.AuthService;
import com.example.shippingservicesystemapi.service.ConfirmationService;
import com.example.shippingservicesystemapi.service.EmailSenderService;
import com.example.shippingservicesystemapi.service.UserService;
import com.example.shippingservicesystemapi.template.EmailTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private UserMapper userMapper; //needed for converting from registerDTO to userDTO
    private ConfirmationService confirmationService;
    private UserService userService;
    private EmailSenderService emailSenderService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserMapper userMapper,
                           ConfirmationService confirmationService,
                           UserService userService,
                           EmailSenderService emailSenderService,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.confirmationService = confirmationService;
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(RegisterDTO registerDTO) {
        final UserDTO userDTO = userService.create(userMapper.toDTO(registerDTO));

        //generating a 15-minute token
        ConfirmationDTO confirmationDTO = confirmationService.create(userDTO);

        //sending an email
        final String link = "http://localhost:8080/api/auth/confirm?token=".concat(confirmationDTO.getToken()); //the link is temporary
        final String content = EmailTemplate.contentForEmailConfirmation(userDTO.getFirstName(), link);
        emailSenderService.send(userDTO.getEmail(), content);
    }

    @Override
    public Object login(LoginDTO loginDTO) {
        final String email = loginDTO.getEmail();
        final UserDTO userDTO = userService.readByEmail(email);

        final boolean passwordEquality = passwordEncoder.matches(loginDTO.getPassword(), userDTO.getPassword());
        if (!passwordEquality) {
            return "Password is incorrect";
        }

        if (!userDTO.isEmailVerification()) {
            // if user didn't confirm, then during login process confirmation would be sent again
            //generating a 15-minute token
            ConfirmationDTO confirmationDTO = confirmationService.create(userDTO);

            //sending an email
            final String link = "http://localhost:8080/api/auth/confirm?token=".concat(confirmationDTO.getToken()); //the link is temporary
            final String content = EmailTemplate.contentForEmailConfirmation(userDTO.getFirstName(), link);
            emailSenderService.send(email, content);

            return "Confirmation required. Check your email box!";
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        loginDTO.getPassword()
                )
        );//if user with such data does not exist, code will not be executed anymore

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateJwt(authentication);
        return new AuthResponseDTO(token);
    }
}
