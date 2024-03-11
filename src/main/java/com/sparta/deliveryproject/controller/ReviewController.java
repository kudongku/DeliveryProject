package com.sparta.deliveryproject.controller;

import com.sparta.deliveryproject.requestDto.ReviewRequestDto;
import com.sparta.deliveryproject.responseDto.ReviewListResponseDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/review/{menuId}")
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰조회
    @GetMapping()
    public ResponseEntity<ReviewListResponseDto> getReviewDetail(@PathVariable Long menuId) {
        ReviewListResponseDto reviewList = reviewService.getReviewDetail(menuId);
        return ResponseEntity.status(200).body(reviewList);
    }

    //리뷰등록
    @PostMapping()
    public void review(@PathVariable Long menuId, @RequestBody ReviewRequestDto reviewRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.review(reviewRequestDto, menuId, userDetails);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@PathVariable Long menuId, @PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.updateReview(reviewRequestDto, menuId, reviewId, userDetails);
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long menuId, @PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(menuId, reviewId, userDetails);
    }

}