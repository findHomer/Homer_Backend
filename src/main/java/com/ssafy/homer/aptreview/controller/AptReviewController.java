package com.ssafy.homer.aptreview.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.aptreview.model.dto.ReviewDto;
import com.ssafy.homer.aptreview.model.service.AptReviewService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
public class AptReviewController {
	
	private final AptReviewService aptReviewService;
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody ReviewDto review){
		boolean result = aptReviewService.registerReview(review);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{aptId}")
	public ResponseEntity<?> list(@PathVariable(name = "aptId",required = true) String aptId){
		List<ReviewDto> result = aptReviewService.allReview(aptId);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(Long reviewId){
		boolean result = aptReviewService.deleteReview(reviewId);
		return ResponseEntity.ok(result);
	}
	
	@PatchMapping
	public ResponseEntity<?> modify(@RequestBody ReviewDto review){
		boolean result= aptReviewService.modifyReview(review);
		return ResponseEntity.ok(result);
	}
}
