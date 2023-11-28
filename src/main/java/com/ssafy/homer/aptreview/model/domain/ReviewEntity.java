package com.ssafy.homer.aptreview.model.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import com.ssafy.homer.user.domain.User;

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
@Entity(name="review")
@DynamicInsert
public class ReviewEntity  {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	
	@Column
	private String aptId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private String contents;

	@Column
	private String photoUrl;

	@Column
	private Float starScore;

	@Column
	private Timestamp createdAt;

	@Override
	public String toString() {
		return "ReviewEntity [reviewId=" + reviewId + ", aptId=" + aptId + ", user=" + user + ", contents=" + contents
				+ ", photoUrl=" + photoUrl + ", starScore=" + starScore + ", createdAt=" + createdAt + "]";
	}
	
	
}
