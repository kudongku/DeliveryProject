package com.sparta.deliveryproject.responseDto;


import com.sparta.deliveryproject.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {
    private Long id;
    private List<ReviewResponseDto> reviewList;

    public ReviewListResponseDto(Menu menu, List<ReviewResponseDto> reviewList) {
        this.id = menu.getId();
        this.reviewList = reviewList;
    }
}
