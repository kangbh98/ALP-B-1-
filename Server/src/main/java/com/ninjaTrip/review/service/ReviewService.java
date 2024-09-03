package com.ninjaTrip.review.service;

import com.ninjaTrip.review.dto.GetReviewResponse;
import com.ninjaTrip.review.dto.Review;

import java.util.List;

public interface ReviewService {
    List<GetReviewResponse> getReviews(String placeName);
    void writeReview(Review review);
}
