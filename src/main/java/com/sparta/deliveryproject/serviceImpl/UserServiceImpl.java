package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.requestDto.ChangePasswordDto;
import com.sparta.deliveryproject.responseDto.UserResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "rfq3wr4yqdiw";
    private static final String ENTRE_TOKEN = "r4iqurqrr4oq";

    @Override
    public void signup(String email, String nickname, String password, String address, String authorityToken) {
        String encodedPassword = passwordEncoder.encode(password);

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        } else if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("이미 사용된 닉네임입니다.");
        }

        UserRoleEnum userRoleEnum;

        if (authorityToken == null) {
            userRoleEnum = UserRoleEnum.USER;
        } else {

            userRoleEnum = switch (authorityToken) {
                case ADMIN_TOKEN -> UserRoleEnum.ADMIN;
                case ENTRE_TOKEN -> UserRoleEnum.ENTRE;
                default -> throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
            };

        }

        User user = new User(email, nickname, encodedPassword, address, userRoleEnum);
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 Id를 가진 유저는 존재하지 않습니다.")
        );
        if (Objects.equals(user.getAuthorities().toString(), "[ADMIN]")) {
            throw new IllegalArgumentException("admin 권한의 유저를 삭제할 순 없습니다.");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        if (user.getPassword().equals(changePasswordDto.getPrePassword())) {
            String encodedPassword = passwordEncoder.encode(changePasswordDto.getPostPassword());
            user.updatePassword(encodedPassword);
        }

        userRepository.save(user);
    }
}