package com.sparta.deliveryproject.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewLikeResponseDto {
    private Long userId;
    private Long reviewId;

    public ReviewLikeResponseDto(Long userId, Long reviewId) {
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
