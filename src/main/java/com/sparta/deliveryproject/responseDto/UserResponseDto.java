package com.sparta.deliveryproject.responseDto;

import com.sparta.deliveryproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String address;
    private String auth;
    private String rank;

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.auth = user.getAuthorities().toString();
        this.rank = user.getGrade().toString();
    }
}
