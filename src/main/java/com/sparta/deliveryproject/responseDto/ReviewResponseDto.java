package com.sparta.deliveryproject.responseDto;

import com.sparta.deliveryproject.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
    private String content;
    private String username;

    public ReviewResponseDto(Review review) {
        this.content = review.getContent();
        this.username = review.getUser().getUsername();
    }
}
