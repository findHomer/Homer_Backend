package com.ssafy.homer.dongcode.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

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
@Entity(name = "dongcode")
public class DongcodeEntity {

	@Column
	String entireCode;
	
	@Column
	String name;
	
	@Column
	String sidoCode;
	
	@Column
	String sigunguCode;
	
	@Column
	String dongCode;
	
	@Column
	String sidoName;
	
	@Column
	String sigunguName;
	
	@Column
	String dongName;
}
