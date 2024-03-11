package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.entity.Review;
import com.sparta.deliveryproject.entity.ReviewLikes;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.ReviewLikesRepository;
import com.sparta.deliveryproject.repository.ReviewRepository;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.ReviewLikesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewLikesServiceImpl implements ReviewLikesService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikesRepository reviewLikesRepository;
    private final UserRepository userRepository;

    // 리뷰 좋아요 등록
    @Override
    public void likeReview(Long reviewId, UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserID();
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다.")
        );

        if (reviewLikesRepository.findByUserAndReview(user, review).isPresent()) {
            throw new IllegalArgumentException("이미 좋아요한 리뷰입니다.");
        }

        ReviewLikes reviewLikes = new ReviewLikes(user, review);
        reviewLikesRepository.save(reviewLikes);
    }

    // 리뷰 좋아요 삭제
    @Override
    public void likeDeleteReview(Long reviewId, UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserID();
        User user = userRepository.findById(userId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new NoSuchElementException("해당 리뷰를 찾을 수 없습니다.")
        );

        ReviewLikes reviewLikes = reviewLikesRepository.findByUserAndReview(user, review).orElseThrow(
                () -> new NoSuchElementException("해당 리뷰에 좋아요를 하지 않았습니다.")
        );

        reviewLikesRepository.deleteById(reviewLikes.getId());
    }

    //리뷰 좋아요 갯수 출력
    @Override
    public int getReviewLikesCount(Long reviewId) {
        return reviewLikesRepository.countByReview_Id(reviewId);
    }
}
