package com.sparta.deliveryproject.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "이메일이 형식과 다릅니다.")
    private String email;

    @NotBlank
    private String nickname;

    @Pattern(regexp = "^[a-z0-9]{4,15}$", message = "아이디가 형식과 다릅니다.")
    private String password;

    @NotBlank
    private String address;

    private String authorityToken;
}
