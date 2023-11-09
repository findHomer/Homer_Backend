package com.ssafy.homer.aptreview.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//		List<ReviewDto> results = new ArrayList<ReviewDto>();
		
		List<ReviewEntity> entities = aptReviewRepository.findByAptId(aptId);
		List<ReviewDto> results = entities.stream()
				.map(m -> ReviewDto.builder()
						.reviewId(m.getReviewId())
						.aptId(m.getAptId())
						.contents(m.getContents())
						.userId(m.getUserId())
						.photoUrl(m.getPhotoUrl())
						.starScore(m.getStarScore())
						.createdAt(m.getCreatedAt())
						.build())
 				.collect(Collectors.toList());
//		
//		for(ReviewEntity entity: entities) {
//			ReviewDto dto = ReviewDto.builder()
//					.aptId(entity.getAptId())
//					.contents(entity.getContents())
//					.userId(entity.getUserId())
//					.photoUrl(entity.getPhotoUrl())
//					.starScore(entity.getStarScore())
//					.createdAt(entity.getCreatedAt())
//					.build();
//			results.add(dto);
//		}
		
		return results;
	}

	@Override
	public boolean deleteReview(Long reviewId) {
		aptReviewRepository.deleteById(reviewId);
		return true;
	}

	@Override
	public boolean modifyReview(ReviewDto review) {
		//TODO 사진 변경시 사진 다시 저장하는 로직 추가
		//
		//
		
		Optional<ReviewEntity> entity = aptReviewRepository.findById(review.getReviewId());
		entity.ifPresent(t ->{ 
			if(review.getStarScore() != null) {
				t.setStarScore(review.getStarScore());
			}
			if(review.getContents() != null) {
				t.setContents(review.getContents());
			}
			if(review.getPhotoUrl() != null) {
				t.setPhotoUrl(review.getPhotoUrl());
			}
			aptReviewRepository.save(t);
		});
		return true;
	}

}
