package com.ssafy.homer.apartInfo.service;

import java.util.List;

import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchDto;


public interface ApartInfoService {

	List<ApartInfoDto> getTotalApart();

	List<ApartInfoDto> getApartInMap(SearchDto searchDto);
	
	
}
