package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserProfileImageResponse;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserProfileService;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam String nickname
    ) {
        return ResponseEntity.ok(userService.searchUser(nickname));
    }

    @PutMapping("/users")
    public void changePassword(@Auth AuthUser authUser, @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }

    @PostMapping("/user/upload/profile")
    public ResponseEntity<UserProfileImageResponse> uploadProfile(@Auth AuthUser authUser, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userProfileService.saveProfile(file, authUser.getId()));

    }
}
