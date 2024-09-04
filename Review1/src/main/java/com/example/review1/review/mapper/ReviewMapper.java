package com.example.review1.review.mapper;

import com.example.review1.review.dto.GetReviewResponse;
import com.example.review1.review.dto.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<GetReviewResponse> getReviews(String place);
    void writeReview(Review review);
}
