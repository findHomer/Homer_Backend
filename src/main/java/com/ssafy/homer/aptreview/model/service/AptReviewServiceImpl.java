package com.ssafy.homer.aptreview.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.homer.aptreview.model.domain.ReviewEntity;
import com.ssafy.homer.aptreview.model.dto.ReviewDto;
import com.ssafy.homer.aptreview.model.repository.AptReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptReviewServiceImpl implements AptReviewService{
	
	private final AptReviewRepository aptReviewRepository;
	
	@Override
	public boolean registerReview(ReviewDto review) {
		ReviewEntity entity = ReviewEntity.builder()
			   .aptId(review.getAptId())
			   .contents(review.getContents())
			   .photoUrl(review.getPhotoUrl())
			   .starScore(review.getStarScore())
			   .build();
	
		aptReviewRepository.save(entity);
		return true;
	}

	@Override
	public List<ReviewDto> allReview(String aptId) {
		List<ReviewDto> results = new ArrayList<ReviewDto>();
		
		List<ReviewEntity> entities = aptReviewRepository.findByAptId(aptId);
		
		for(ReviewEntity entity: entities) {
			ReviewDto dto = ReviewDto.builder()
					.aptId(entity.getAptId())
					.contents(entity.getContents())
					.userId(entity.getUserId())
					.photoUrl(entity.getPhotoUrl())
					.starScore(entity.getStarScore())
					.createdAt(entity.getCreatedAt())
					.build();
			results.add(dto);
		}
		
		return results;
	}

	@Override
	public boolean deleteReview(Long reviewId) {
		return false;
	}

	@Override
	public boolean modifyReview(ReviewDto review) {
		return false;
	}

}
