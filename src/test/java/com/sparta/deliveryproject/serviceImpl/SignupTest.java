package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.requestDto.SignupRequestDto;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class SignupTest {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "kudongku@gmail.com",
                "kudongku",
                "1234",
                "경기도 하남시 미사강변동로 19",
                "r4iqurqrr4oq");

        userServiceImpl.signup(signupRequestDto.getEmail(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getAddress(), signupRequestDto.getAuthorityToken());

        User user = userRepository.findByEmail("kudongku@gmail.com").orElseThrow(
                () -> new IllegalArgumentException("저장이 안됨")
        );

        assertThat(user.getRole()).isEqualTo(UserRoleEnum.ENTRE);
    }

    @Test
    @DisplayName("회원가입 실패, 유효한 토큰이 아님")
    void signup_fail_notValidToken() {
        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "kudongku@gmail.com",
                "kudongku",
                "1234",
                "경기도 하남시 미사강변동로 19",
                "1234");

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.signup(signupRequestDto.getEmail(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getAddress(), signupRequestDto.getAuthorityToken()));
        assertThat(e.getMessage()).isEqualTo("유효한 토큰이 아닙니다.");
    }

    @Test
    @DisplayName("회원가입 실패, email 중복")
    void signup_fail_duplicatedEmail() {
        SignupRequestDto signupRequestDto1 = new SignupRequestDto(
                "kudongku@gmail.com",
                "kudongku",
                "1234",
                "경기도 하남시 미사강변동로 19",
                null);

        userServiceImpl.signup(signupRequestDto1.getEmail(), signupRequestDto1.getNickname(), signupRequestDto1.getPassword(), signupRequestDto1.getAddress(), signupRequestDto1.getAuthorityToken());

        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "kudongku@gmail.com",
                "kudongku2",
                "1234",
                "경기도 하남시 미사강변동로 19",
                null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.signup(signupRequestDto.getEmail(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getAddress(), signupRequestDto.getAuthorityToken()));
        assertThat(e.getMessage()).isEqualTo("이미 가입된 이메일입니다.");
    }

    @Test
    @DisplayName("회원가입 실패, nickname 중복")
    void signup_fail_duplicatedNickname() {
        SignupRequestDto signupRequestDto1 = new SignupRequestDto(
                "kudongku@gmail.com",
                "kudongku",
                "1234",
                "경기도 하남시 미사강변동로 19",
                null);

        userServiceImpl.signup(signupRequestDto1.getEmail(), signupRequestDto1.getNickname(), signupRequestDto1.getPassword(), signupRequestDto1.getAddress(), signupRequestDto1.getAuthorityToken());

        SignupRequestDto signupRequestDto = new SignupRequestDto(
                "kudongku2@gmail.com",
                "kudongku",
                "1234",
                "경기도 하남시 미사강변동로 19",
                null);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.signup(signupRequestDto.getEmail(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getAddress(), signupRequestDto.getAuthorityToken()));
        assertThat(e.getMessage()).isEqualTo("이미 사용된 닉네임입니다.");
    }
}
