package com.ssafy.homer.apartInfo.service;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.dto.*;
import com.ssafy.homer.apartInfo.exception.BaseException;
import com.ssafy.homer.apartInfo.exception.ErrorCode;
import org.springframework.stereotype.Service;

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
	public List<ApartInfoDto> getApartInMap(SearchMapDto searchMapDto) {
		
		return apartInfoRepository.searchAll(searchMapDto);
	}

	@Override
	public ApartInfoDetailDto getApartDetail(String apartId) {
		 ApartInfo apartInfo =  apartInfoRepository.findById(apartId).orElseThrow(() -> new BaseException(ErrorCode.APART_NOT_FOUND));

		 List<ApartDealAreaDto> apartDealAreaDtoList = new ArrayList<ApartDealAreaDto>();
		 //apartDealAreaDtoList 구현필요

		 ApartInfoDetailDto apartInfoDetailDto = ApartInfoDetailDto.builder()
				 .aptId(apartInfo.getAptId())
				 .aisleType(apartInfo.getAisleType())
				 .allowDate(apartInfo.getAllowDate())
				 .aptName(apartInfo.getAptName())
				 .canPark((float)apartInfo.getCarCount()/apartInfo.getHouseholdCount())//소수점자릿수 체크
				 .lawAddr(apartInfo.getLawAddr())
				 .roadAddr(apartInfo.getRoadAddr())
				 .maxFloor(apartInfo.getMaxFloor())
				 .latitude(apartInfo.getLatitude())
				 .longitude(apartInfo.getLongitude())
				 .householdCount(apartInfo.getHouseholdCount())
				 .dealInfos(apartDealAreaDtoList)
				 .build();


		return apartInfoDetailDto;
	}

}
