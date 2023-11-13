package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchMapDto;

public interface ApartInfoRepositoryCustom {
	List<ApartInfoDto> searchMap(SearchMapDto searchMapDto);
}
