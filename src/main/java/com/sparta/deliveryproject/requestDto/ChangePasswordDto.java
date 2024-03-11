package com.sparta.deliveryproject.requestDto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDto {
    private String prePassword;
    private String postPassword;
}
