package com.yuzarsif.contextshare.userservice.controller;

import com.yuzarsif.contextshare.userservice.dto.UserDto;
import com.yuzarsif.contextshare.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> userCount() {
        return ResponseEntity.ok(userService.userCount());
    }
}
