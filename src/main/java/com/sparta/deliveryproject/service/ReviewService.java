package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.requestDto.ReviewRequestDto;
import com.sparta.deliveryproject.responseDto.ReviewListResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;

public interface ReviewService {
    void review(ReviewRequestDto reviewRequestDto, Long menuId, UserDetailsImpl userDetails);

    void updateReview(ReviewRequestDto reviewRequestDto, Long menuId, Long reviewId, UserDetailsImpl userDetails);

    void deleteReview(Long menuId, Long reviewId, UserDetailsImpl userDetails);

    ReviewListResponseDto getReviewDetail(Long id);
}
