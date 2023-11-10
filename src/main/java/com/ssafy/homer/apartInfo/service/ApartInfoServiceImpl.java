package com.ssafy.homer.apartInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchDto;
import com.ssafy.homer.apartInfo.repository.ApartInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApartInfoServiceImpl implements ApartInfoService{
	
	private final ApartInfoRepository apartInfoRepository;
	@Override
	public List<ApartInfoDto> getTotalApart() {
		return apartInfoRepository.findSimpleAll();
	}
	
	@Override
	public List<ApartInfoDto> getApartInMap(SearchDto searchDto) {
		
		return apartInfoRepository.searchAll(searchDto);
		//return null;
	}

}
