package com.codeit.duckhu.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codeit.duckhu.review.dto.ReviewCreateRequest;
import com.codeit.duckhu.review.dto.ReviewDto;
import com.codeit.duckhu.review.entity.Review;
import com.codeit.duckhu.review.mapper.ReviewMapper;
import com.codeit.duckhu.review.repository.ReviewRepository;
import com.codeit.duckhu.review.service.impl.ReviewServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 리뷰 서비스 테스트 클래스
 * TDD 방식으로 구현 예정
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

  /**
   * 서비스 계층 설계
   *
   * 1. ReviewService
   * - createReview: 리뷰 생성
   * - getReview: ID로 리뷰 조회
   * - updateReview: 리뷰 업데이트
   * - deleteReview: 리뷰 삭제
   * - likeReview: 리뷰 좋아요
   * - getAll: 목록 조회(커서 페이지네이션)
   *
   * 2. DTO 클래스 설계
   * - ReviewDto: 응답 데이터
   * - ReviewCreateRequest: 생성 요청 데이터
   * - ReviewUpdateRequest: 업데이트
   * 요청 데이터 - ReviewLikeDto: 리뷰 좋아요 데이터
   *
   * 3. 서비스 구현체 (ReviewServiceImpl) 생성 - 리포지토리를 통한 CRUD 구현
   */

  @Mock
  private ReviewRepository reviewRepository;
  
  @Mock
  private ReviewMapper reviewMapper;

  @InjectMocks
  private ReviewServiceImpl reviewService;

  private Review testReview;
  private ReviewDto testReviewDto;
  private ReviewCreateRequest testCreateRequest;
  private UUID testReviewId;

  @BeforeEach
  void setUp() {
    // 테스트용 ID 생성
    testReviewId = UUID.randomUUID();
    
    // 테스트용 리뷰 엔티티 생성
    testReview = Review.builder()
        .content("볼만해요")
        .rating(3)
        .likeCount(0)
        .commentCount(0)
        .likeByMe(false)
        .build();

    // 테스트용 DTO 생성
    testReviewDto = ReviewDto.builder()
        .content("볼만해요")
        .rating(3)
        .commentCount(0)
        .likeCount(0)
        .likedByMe(false)
        .build();
        
    // 테스트용 Create 요청 생성
    testCreateRequest = ReviewCreateRequest.builder()
        .content("볼만해요")
        .rating(3)
        .build();
  }

  @Test
  @DisplayName("리뷰 생성 성공")
  void createReview_shouldCreateReview() {
    // Given
    when(reviewRepository.save(any(Review.class))).thenReturn(testReview);
    when(reviewMapper.toDto(any(Review.class))).thenReturn(testReviewDto);

    // When
    ReviewDto result = reviewService.createReview(testCreateRequest);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getRating()).isEqualTo(testCreateRequest.getRating());
    assertThat(result.getContent()).isEqualTo(testCreateRequest.getContent());
    assertThat(result.getLikeCount()).isEqualTo(0);
    assertThat(result.getCommentCount()).isEqualTo(0);
    verify(reviewRepository).save(any(Review.class));
    verify(reviewMapper).toDto(any(Review.class));
  }

  @Test
  @DisplayName("ID로 리뷰 조회 테스트")
  void getReviewById_shouldReturnReview() {
    // Given
    when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.of(testReview));
    when(reviewMapper.toDto(testReview)).thenReturn(testReviewDto);
    
    // When
    ReviewDto result = reviewService.getReviewById(testReviewId);
    
    // Then
    assertThat(result).isNotNull();
    assertThat(result.getContent()).isEqualTo("볼만해요");
    assertThat(result.getRating()).isEqualTo(3);
  }
}

