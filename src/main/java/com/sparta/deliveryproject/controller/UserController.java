package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.requestDto.ChangePasswordDto;
import com.sparta.deliveryproject.requestDto.SignupRequestDto;
import com.sparta.deliveryproject.responseDto.UserResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userServiceImpl;

    @PostMapping("/public/users/signup")
    public void signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            throw new IllegalArgumentException("회원가입 정보 입력이 올바르지 않습니다." + sb);
        }

        userServiceImpl.signup(requestDto.getEmail(), requestDto.getNickname(), requestDto.getPassword(), requestDto.getAddress(), requestDto.getAuthorityToken());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/users/admin")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> userResponseDtoList = userServiceImpl.getUsers();
        return ResponseEntity.status(200).body(userResponseDtoList);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/admin/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userServiceImpl.deleteUser(userId);
    }

    @PutMapping("/users/change-password")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userServiceImpl.changePassword(changePasswordDto, userDetails);
    }
}
