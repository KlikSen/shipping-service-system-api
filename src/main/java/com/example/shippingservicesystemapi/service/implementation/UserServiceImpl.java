package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.ConfirmationDTO;
import com.example.shippingservicesystemapi.dto.ShopDTO;
import com.example.shippingservicesystemapi.dto.ShopOwnerDTO;
import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.entity.User;
import com.example.shippingservicesystemapi.enumeration.UserRole;
import com.example.shippingservicesystemapi.mapper.ShopMapper;
import com.example.shippingservicesystemapi.mapper.UserMapper;
import com.example.shippingservicesystemapi.repository.UserRepository;
import com.example.shippingservicesystemapi.service.ConfirmationService;
import com.example.shippingservicesystemapi.service.EmailSenderService;
import com.example.shippingservicesystemapi.service.ShopService;
import com.example.shippingservicesystemapi.service.UserService;
import com.example.shippingservicesystemapi.template.EmailTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopMapper shopMapper;
    private final ShopService shopService;
    private final ConfirmationService confirmationService;
    private final EmailSenderService emailSenderService;
    private final String emailConfirmationURL = "http://localhost:8080/confirm?token=";
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ShopMapper shopMapper,
                           ShopService shopService,
                           @Lazy ConfirmationService confirmationService,
                           EmailSenderService emailSenderService,
                           UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.confirmationService = confirmationService;
        this.emailSenderService = emailSenderService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO create(final UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("User with such an email already exists");
        }
        if (userDTO.getUserRole() == UserRole.OWNER) {
            throw new IllegalStateException("User cannot get registered as an owner in such a way");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisterTime(LocalDateTime.now());
        user = userRepository.save(user);

        //generating a 15-minute token
        ConfirmationDTO confirmationDTO = confirmationService.create(user);

        //sending an email
        sendingEmail(confirmationDTO.getToken(), user.getFirstName(), user.getEmail());

        return userMapper.toDTO(user);
    }

    private void sendingEmail(final String confirmationToken, final String name, final String email) {
        final String link = emailConfirmationURL.concat(confirmationToken); //the link is temporary
        final String content = EmailTemplate.contentForEmailConfirmation(name, link);
        emailSenderService.send(email, content);
    }

    @Override
    public UserDTO read(final Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new IllegalStateException("User with such an id not found"));
    }

    @Override
    public UserDTO update(final UserDTO userDTO) {
        userRepository.update(
                userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getLastName()
        );
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalStateException("User not found by id"));

        return userMapper.toDTO(user);
    }

    @Override
    public void updateEmail(long id, String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is being used by either you or somebody else");
        }
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found by id"));

        user.setEmail(email);
        user.setEmailVerification(false);
    }

    @Override
    public void updatePassword(long id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found by id"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("Password hasn't been changed");
        }
        user.setPassword(passwordEncoder.encode(password));
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
    public UserDTO readByEmail(final String email) {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with such an email not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO createShopOwner(ShopOwnerDTO shopOwnerDTO) {
        //creating a user
        User user = userMapper.toEntity(shopOwnerDTO);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("User with such an email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); //setting an encoded password
        user.setUserRole(UserRole.OWNER);
        user = userRepository.save(user);

        //generating a 15-minute token
        ConfirmationDTO confirmationDTO = confirmationService.create(user);
        //creating a shop
        ShopDTO shopDTO = shopMapper.toDTO(shopOwnerDTO);
        shopDTO.setShopOwnerId(user.getId()); //assigning shopOwnerId to the particular shop
        shopService.create(shopDTO);

        //sending an email
        sendingEmail(confirmationDTO.getToken(), user.getFirstName(), user.getEmail());

        return userMapper.toDTO(user);
    }
}
