package com.example.review1.review.service;

import com.example.review1.review.dto.GetReviewResponse;
import com.example.review1.review.dto.Review;
import com.example.review1.review.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper mapper;

    public ReviewServiceImpl(ReviewMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<GetReviewResponse> getReviews(String placeName) {
        return mapper.getReviews(placeName);
    }

    @Override
    public void writeReview(Review review) {
        review.setTotal(
                (int) Math.round((review.getFood() + review.getTraffic() + review.getTravel()) / 3)
        );
        mapper.writeReview(review);
    }
}
