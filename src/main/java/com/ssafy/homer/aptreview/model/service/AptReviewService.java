package com.ssafy.homer.aptreview.model.service;

import java.util.List;

import com.ssafy.homer.aptreview.model.dto.ReviewDto;

public interface AptReviewService {
	boolean registerReview(ReviewDto review); // 리뷰 등록
	List<ReviewDto> allReview(String aptId); // 리뷰 전체 조회
	boolean deleteReview(Long reviewId); // 리뷰 삭제
	boolean modifyReview(ReviewDto review); //리뷰 수정
}
