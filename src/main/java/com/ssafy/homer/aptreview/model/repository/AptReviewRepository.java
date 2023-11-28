package com.ssafy.homer.aptreview.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.aptreview.model.domain.ReviewEntity;

@Repository
public interface AptReviewRepository extends JpaRepository<ReviewEntity, Long> {
	List<ReviewEntity> findByAptId(String aptId);
}
