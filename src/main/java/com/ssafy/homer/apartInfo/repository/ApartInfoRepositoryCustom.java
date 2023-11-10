package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchDto;

public interface ApartInfoRepositoryCustom {
	List<ApartInfoDto> searchAll(SearchDto searchDto);
}
