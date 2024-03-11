package com.sparta.deliveryproject.responseDto;

import com.sparta.deliveryproject.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponseDto {
    private String name;
    private String introduce;
    private int price;

    public MenuResponseDto(Menu menu) {
        this.name = menu.getName();
        this.introduce = menu.getIntroduce();
        this.price = menu.getPrice();
    }
}
