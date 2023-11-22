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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.homer.aptreview.model.dto.ReviewDto;
import com.ssafy.homer.aptreview.model.service.AptReviewService;
import com.ssafy.homer.s3.util.S3Uploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
public class AptReviewController {
	
	private final AptReviewService aptReviewService;
	private final S3Uploader s3uploader;
	
	@PostMapping
	public ResponseEntity<?> register(@RequestPart("review") ReviewDto review, @RequestPart(value="image") MultipartFile image){
		String url = null;
		try {
			url = s3uploader.upload(image, "review");
			review.setPhotoUrl(url);
		}catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
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
