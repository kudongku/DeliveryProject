package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.requestDto.ChangePasswordDto;
import com.sparta.deliveryproject.responseDto.UserResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;

import java.util.List;

public interface UserService {
    void signup(String email, String nickname, String password, String address, String authorityToken);

    List<UserResponseDto> getUsers();

    void deleteUser(Long userId);

    void changePassword(ChangePasswordDto changePasswordDto, UserDetailsImpl userDetails);
}
