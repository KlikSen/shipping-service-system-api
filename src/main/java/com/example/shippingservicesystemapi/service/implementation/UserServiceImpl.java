package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.entity.User;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.repository.UserRepository;
import com.example.shippingservicesystemapi.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    final private UserMapper userMapper=Mappers.getMapper(UserMapper.class);

    @Override
    public UserDTO create(final UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("User with such an email already exists");
        }
        final User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisterTime(LocalDateTime.now());
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO read(final Long id) {
        return userRepository.findById(id).map(userMapper::toDTO)
                .orElseThrow();
    }
    @Override
    public UserDTO update(final UserDTO userDTO) {
        final User user = userMapper.toEntity(userDTO);
        final User userFromDatabase = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalStateException("User not found by id"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisterTime(userFromDatabase.getRegisterTime());

        final boolean emailVerification = userFromDatabase.isEmailVerification();  // value of emailVerification in db currently
        final boolean emailEquality = userFromDatabase.getEmail().equals(user.getEmail()); //if email in db and dto are same
        user.setEmailVerification(emailEquality && emailVerification);
        return userMapper.toDTO(userRepository.save(user));
    }
    @Override
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> readAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public void updateEmailVerificationById(Long id, boolean emailVerification) {
        userRepository.updateEmailVerificationById(id, emailVerification);
    }

    @Override
    public UserDTO readByEmail(final String email){
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with such an email not found"));
        return userMapper.toDTO(user);
    }
}
