package com.ssafy.homer.aptreview.model.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ReviewEntity  {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	
	@Column
	private String aptId;

	@Column
	private Long userId;

	@Column
	private String contents;

	@Column
	private String photoUrl;

	@Column
	private Float starScore;

	@Column
	private Date createdAt;
}
