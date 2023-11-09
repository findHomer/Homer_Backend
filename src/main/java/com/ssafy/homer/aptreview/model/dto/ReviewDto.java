package com.ssafy.homer.aptreview.model.dto;

import java.sql.Date;

import com.ssafy.homer.aptreview.model.ReviewToDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto implements ReviewToDto{
	
	//리뷰Id
	private Long reviewId;
	
	//아파트 id
	private String aptId;

	//유저 id
	private Long userId;
	
	//게시판 내용
	private String contents;
	
	//사진
	private String photoUrl;

	//별점
	private Float starScore;
	
	//등록일자
	private Date createdAt;
}
