package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.security.UserDetailsImpl;

public interface ReviewLikesService {
    void likeReview(Long reviewId, UserDetailsImpl userDetails);
    void likeDeleteReview(Long reviewId, UserDetailsImpl userDetails);
    int getReviewLikesCount(Long reviewId);
}
