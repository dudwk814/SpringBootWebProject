package com.zerock.mreview.repository;

import com.zerock.mreview.entity.Member;
import com.zerock.mreview.entity.Movie;
import com.zerock.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMovieReviews() {

        // 200개의 리뷰를 등록
        IntStream.rangeClosed(1, 200).forEach(i -> {

            // 영화 번호
            Long mno = (long) (Math.random() * 100) + 1;

            // 리뷰어 번호
            Long mid = (long) ((Math.random() * 100) + 1);
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .movie(Movie.builder().mno(mno).build())
                    .member(member)
                    .grade((int) (Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void deleteReviews() {

        for (int i = 1; i <= 200; i++) {

            Long id = (long) i;
            reviewRepository.deleteById(id);
        }
    }

    @Test
    public void testGetMovieReview() {

        Movie movie = Movie.builder().mno(1L).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println(movieReview.getGrade());
            System.out.println(movieReview.getText());
            System.out.println(movieReview.getMember().getEmail());
            System.out.println("-------------------------------------------------");
        });
    }
}
