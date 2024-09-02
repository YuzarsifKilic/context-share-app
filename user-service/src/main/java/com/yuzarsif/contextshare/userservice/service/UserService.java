package com.yuzarsif.contextshare.userservice.service;

import com.yuzarsif.contextshare.userservice.dto.CreateUserRequest;
import com.yuzarsif.contextshare.userservice.dto.UserDto;
import com.yuzarsif.contextshare.userservice.model.User;
import com.yuzarsif.contextshare.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodeService codeService;

    public UserDto findUserById(String id) {
        return UserDto.convert(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id)));
    }

    public UserDto createUser(CreateUserRequest request) {
        User user = User
                .builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .role(request.role())
                .enable(false)
                .build();

        User savedUser = userRepository.save(user);

        codeService.createCode(savedUser);

        return UserDto.convert(savedUser);
    }

    public void enableUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        user.setEnable(true);
        userRepository.save(user);
    }


}
