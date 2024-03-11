package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.ReviewLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviewLike/{reviewId}")
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikesService reviewLikesService;

    //리뷰 좋아요 등록
    @PostMapping()
    public void likeReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewLikesService.likeReview(reviewId, userDetails);
    }

    //리뷰 좋아요 취소
    @DeleteMapping()
    public void likeDeleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewLikesService.likeDeleteReview(reviewId, userDetails);
    }

    //해당 리뷰 좋아요 갯수
    @GetMapping("/count")
    public ResponseEntity<Integer> getReviewLikesCount(@PathVariable Long reviewId) {
        int reviewLikesCount = reviewLikesService.getReviewLikesCount(reviewId);
        return ResponseEntity.status(200).body(reviewLikesCount);
    }

}
