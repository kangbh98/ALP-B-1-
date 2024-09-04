package com.example.review1.review.service;

import com.example.review1.review.dto.Review;
import com.example.review1.review.dto.GetReviewResponse;
import com.example.review1.review.dto.Review;

import java.util.List;

public interface ReviewService {
    List<GetReviewResponse> getReviews(String placeName);
    void writeReview(Review review);

}
