package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchMapDto;
import com.ssafy.homer.apartInfo.dto.SearchNameDto;

public interface ApartInfoRepositoryCustom {
	List<ApartInfoDto> searchMap(SearchMapDto searchMapDto);

	List<ApartInfoDto> searchName(SearchNameDto searchNameDto);
}
