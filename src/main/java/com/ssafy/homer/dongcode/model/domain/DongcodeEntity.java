package com.ssafy.homer.dongcode.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GeneratorType;

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

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition ="char(10)")
	String entireCode;
	
	@Column
	String name;
	
	@Column(columnDefinition ="char(2)")
	String sidoCode;
	
	@Column(columnDefinition ="char(3)")
	String sigunguCode;

	@Column(columnDefinition ="char(5)")
	String dongCode;
	
	@Column
	String sidoName;
	
	@Column
	String sigunguName;
	
	@Column
	String dongName;
}
