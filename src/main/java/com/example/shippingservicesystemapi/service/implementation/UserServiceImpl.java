package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.DTO.UserDTO;
import com.example.shippingservicesystemapi.entity.User;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.repository.UserRepository;
import com.example.shippingservicesystemapi.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    final private UserMapper userMapper=Mappers.getMapper(UserMapper.class);

    @Override
    public UserDTO create(final UserDTO userDTO) {
        final User user = userMapper.toEntity(userDTO);
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
        user.setRegisterTime(userRepository.findById(user.getId()).map(User::getRegisterTime).orElseThrow()); //value in the db, which cannot be changed by the function mapper

        final boolean emailVerification = userRepository.findById(user.getId()).map(User::isEmailVerification).orElseThrow();  // value of emailVerification in db currently
        final boolean emailEquality = userRepository.findById(user.getId()).map(User::getEmail).orElseThrow().equals(user.getEmail()); //if email in db and dto are same
        user.setEmailVerification(emailEquality && emailVerification);
        return userMapper.toDTO(userRepository.save(user));
    }
    @Override
    public void delete(final Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow());  //if it were deleteById, it would be impossible to retrieve the right answer
    }

    @Override
    public List<UserDTO> readAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
