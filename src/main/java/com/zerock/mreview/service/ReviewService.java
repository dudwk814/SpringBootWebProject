package com.zerock.mreview.service;

import com.zerock.mreview.dto.ReviewDTO;
import com.zerock.mreview.entity.Member;
import com.zerock.mreview.entity.Movie;
import com.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewService {

    // 특정 영화의 모든 리뷰를 조회
    List<ReviewDTO> getListOfMovie(Long mno);

    // 리뷰 추가
    Long register(ReviewDTO movieReviewDTO);

    // 특정 영화리뷰 수정
    void modify(ReviewDTO movieReviewDTO);

    // 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO movieReviewDTO) {

        Review review = Review.builder()
                .reviewnum(movieReviewDTO.getReviewnum())
                .movie(Movie.builder().mno(movieReviewDTO.getMno()).build())
                .member(Member.builder().mid(movieReviewDTO.getMid()).build())
                .grade(movieReviewDTO.getGrade())
                .text(movieReviewDTO.getText())
                .build();

        return review;
    }

    default ReviewDTO entityToDTO(Review movieReview) {

        ReviewDTO movieReviewDTO = ReviewDTO.builder()
                .reviewnum(movieReview.getReviewnum())
                .mno(movieReview.getMovie().getMno())
                .mid(movieReview.getMember().getMid())
                .nickname(movieReview.getMember().getNickname())
                .email(movieReview.getMember().getEmail())
                .grade(movieReview.getGrade())
                .text(movieReview.getText())
                .regDate(movieReview.getRegDate())
                .modDate(movieReview.getModDate())
                .build();

        return movieReviewDTO;
    }
}
