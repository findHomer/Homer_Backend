package com.ssafy.homer.aptreview.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto{
	
	//리뷰Id
	private Long reviewId;
	
	//아파트 id
	private String aptId;

	//유저 id
	private Long userId;
	
	//유저 email
	private String email;
	
	//유저 nickname
	private String nickname;
	
	//유저 profile url
	private String profileUrl;
	
	//게시판 내용
	private String contents;
	
	//사진
	private String photoUrl;

	//별점
	private Float starScore;
	
	//등록일자
	private Timestamp createdAt;

}
