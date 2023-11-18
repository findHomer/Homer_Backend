package com.ssafy.homer.apartInfo.service;

import java.util.List;

import com.ssafy.homer.apartInfo.dto.ApartInfoDetailDto;
import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchMapDto;
import com.ssafy.homer.apartInfo.dto.SearchNameDto;


public interface ApartInfoService {

	List<ApartInfoDto> findTotalApart();

	List<ApartInfoDto> findApartInMap(SearchMapDto searchMapDto);

	List<ApartInfoDto> findApartByName(SearchNameDto searchNameDto);
	ApartInfoDetailDto findApartDetail(String apartId);
	
	
}
