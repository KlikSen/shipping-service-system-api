package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.ConfirmationDTO;
import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.entity.Confirmation;
import com.example.shippingservicesystemapi.mapper.ConfirmationMapper;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.repository.ConfirmationRepository;
import com.example.shippingservicesystemapi.service.ConfirmationService;
import com.example.shippingservicesystemapi.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    @Autowired
    private ConfirmationRepository confirmationRepository;
    @Autowired
    private ConfirmationMapper confirmationMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ConfirmationDTO create(UserDTO userDTO) {
        final String token = UUID.randomUUID().toString();
        final Confirmation  confirmation = Confirmation.builder()
                .token(token)
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusMinutes(15))
                .user(userMapper.toEntity(userDTO))
                .build();

        return confirmationMapper.toDTO(confirmationRepository.save(confirmation));
    }

    @Override
    @Transactional
    public void confirm(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token)
                .orElseThrow(() ->new NoSuchElementException("Confirmation with the token not found"));

        if (confirmation.getUser().isEmailVerification()) {
            throw new IllegalStateException("Email has already been confirmed");
        }

        final LocalDateTime expired = confirmation.getExpired();
        if (expired.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }
        confirmation.setConfirmed(LocalDateTime.now());

        confirmationRepository.save(confirmation);
        userService.updateEmailVerificationById(confirmation.getUser().getId(), true);
    }

}
