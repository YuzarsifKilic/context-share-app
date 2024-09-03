package com.yuzarsif.contextshare.userservice.service;

import com.yuzarsif.contextshare.userservice.dto.CreateUserRequest;
import com.yuzarsif.contextshare.userservice.dto.EnableUserRequest;
import com.yuzarsif.contextshare.userservice.dto.UserDto;
import com.yuzarsif.contextshare.userservice.model.Code;
import com.yuzarsif.contextshare.userservice.model.Purpose;
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

        codeService.createCode(savedUser, Purpose.VERIFY_EMAIL);

        return UserDto.convert(savedUser);
    }

    public void enableUser(EnableUserRequest request) {
        Code code = codeService.findCode(request.email());
        if (code.getCode().equals(request.code()) && code.getPurpose().equals(Purpose.VERIFY_EMAIL)) {
            User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new EntityNotFoundException("User not found with email:" + request.email()));
            user.setEnable(true);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid code");
        }
    }

    public Long userCount() {
        return userRepository.count();
    }


}
