package com.ssafy.homer.apartInfo.service;

import java.util.List;

import com.ssafy.homer.apartInfo.dto.ApartInfoDetailDto;
import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchMapDto;


public interface ApartInfoService {

	List<ApartInfoDto> getTotalApart();

	List<ApartInfoDto> getApartInMap(SearchMapDto searchMapDto);

	ApartInfoDetailDto getApartDetail(String apartId);
	
	
}
