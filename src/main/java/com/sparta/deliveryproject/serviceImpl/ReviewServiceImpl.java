package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.responseDto.ReviewListResponseDto;
import com.sparta.deliveryproject.requestDto.ReviewRequestDto;
import com.sparta.deliveryproject.responseDto.ReviewResponseDto;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Review;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.ReviewRepository;
import com.sparta.deliveryproject.repository.UserRepository;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.ReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Override
    public void review(ReviewRequestDto reviewRequestDto, Long menuId, UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserID();
        User user = userRepository.findById(userId).orElseThrow();
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NoSuchElementException("없는 메뉴입니다."));

        Review review = new Review(reviewRequestDto.getContent(), user, menu);
        reviewRepository.save(review);
    }

    @Override
    public void updateReview(ReviewRequestDto reviewRequestDto, Long menuId, Long reviewId, UserDetailsImpl userDetails) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException("등록된 댓글이 아닙니다."));
        User user = userDetails.getUser();
        isReviewInMenu(menuId, review);

        isReveiwMyselfValidate(user, review);

        review.updateContent(reviewRequestDto.getContent());
    }

    @Override
    public void deleteReview(Long menuId, Long reviewId, UserDetailsImpl userDetails) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NoSuchElementException("등록된 리뷰가 아닙니다."));
        User user = userDetails.getUser();
        isReviewInMenu(menuId, review);
        isReveiwMyselfValidate(user, review);

        reviewRepository.delete(review);
    }

    //해당 메뉴 리뷰
    @Override
    public ReviewListResponseDto getReviewDetail(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 메뉴가 존재하지 않습니다.")
        );

        List<ReviewResponseDto> reviewList = reviewRepository.findAllByMenuId(id)
                .stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());

        return new ReviewListResponseDto(menu, reviewList);
    }

    private void isReviewInMenu(Long menuId, Review review) {
        if (!Objects.equals(review.getMenu().getId(), menuId)) {
            throw new AccessDeniedException("해당 가게의 리뷰가 아닙니다");
        }
    }

    private void isReveiwMyselfValidate(User user, Review review) {
        if (!(user.getUserID().equals(review.getUser().getUserID()))) {
            throw new AccessDeniedException("자신의 리뷰가 아닙니다.");
        }
    }


}
