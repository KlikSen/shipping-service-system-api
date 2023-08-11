package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.ConfirmationDTO;
import com.example.shippingservicesystemapi.entity.Confirmation;
import com.example.shippingservicesystemapi.entity.User;
import com.example.shippingservicesystemapi.mapper.ConfirmationMapper;
import com.example.shippingservicesystemapi.repository.ConfirmationRepository;
import com.example.shippingservicesystemapi.repository.UserRepository;
import com.example.shippingservicesystemapi.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class ConfirmationServiceImpl implements ConfirmationService {
    @Autowired
    private ConfirmationRepository confirmationRepository;
    @Autowired
    private ConfirmationMapper confirmationMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ConfirmationDTO create(User user) {
        final String token = UUID.randomUUID().toString();
        final Confirmation  confirmation = Confirmation.builder()
                .token(token)
                .created(LocalDateTime.now())
                .expired(LocalDateTime.now().plusMinutes(15))
                .user(userRepository.getReferenceById(user.getId()))
                .build();

        return confirmationMapper.toDTO(confirmationRepository.save(confirmation));
    }

    @Override
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
        userRepository.updateEmailVerificationById(confirmation.getUser().getId(), true);
    }

}
