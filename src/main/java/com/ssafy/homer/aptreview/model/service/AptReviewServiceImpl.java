package com.ssafy.homer.aptreview.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.homer.aptreview.model.domain.ReviewEntity;
import com.ssafy.homer.aptreview.model.dto.ReviewDto;
import com.ssafy.homer.aptreview.model.repository.AptReviewRepository;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptReviewServiceImpl implements AptReviewService{
	
	private final AptReviewRepository aptReviewRepository;
	private final UserRepository userRepository;
	
	@Override
	public boolean registerReview(ReviewDto review) {
		
		Optional<User> user = userRepository.findByEmail(review.getEmail());
		
		ReviewEntity entity = ReviewEntity.builder()
			   .aptId(review.getAptId())
			   .user(user.get())
			   .contents(review.getContents())
			   .photoUrl(review.getPhotoUrl())
			   .starScore(review.getStarScore())
			   .build();
	
		aptReviewRepository.save(entity);
		return true;
	}

	@Override
	public List<ReviewDto> allReview(String aptId) {

		List<ReviewEntity> entities = aptReviewRepository.findByAptId(aptId);
		List<ReviewDto> results = entities.stream()
				.map(m -> ReviewDto.builder()
						.reviewId(m.getReviewId())
						.aptId(m.getAptId())
						.contents(m.getContents())
						.userId(m.getUser().getUserId())
						.nickname(m.getUser().getNickname())
						.profileUrl(m.getUser().getUserPhoto())
						.email(m.getUser().getEmail())
						.photoUrl(m.getPhotoUrl())
						.starScore(m.getStarScore())
						.createdAt(m.getCreatedAt())
						.build())
 				.collect(Collectors.toList());
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
