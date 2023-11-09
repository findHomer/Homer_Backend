package com.ssafy.homer.aptreview.model;

import java.sql.Date;

public interface ReviewToDto {
	Long getReviewId();
	String getAptId();
	Long getUserId();
	String getContents();
	String getphotoUrl();
	Float getStarScore();
	Date getCreatedAt();
}
