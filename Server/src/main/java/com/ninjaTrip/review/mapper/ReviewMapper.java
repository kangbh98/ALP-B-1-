package com.ninjaTrip.review.mapper;

import com.ninjaTrip.review.dto.GetReviewResponse;
import com.ninjaTrip.review.dto.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<GetReviewResponse> getReviews(String place);
    void writeReview(Review review);
}
