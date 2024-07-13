package com.ssafy.homer.apartInfo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.ssafy.homer.apartInfo.dto.*;


public interface ApartInfoService {

	List<ApartInfoDto> findTotalApart();

	List<ApartInfoDto> findApartInMap(SearchMapDto searchMapDto);

	List<ApartInfoDto> findApartByName(SearchNameDto searchNameDto);
	ApartInfoDetailDto findApartDetail(String apartId);
	
	MapLocationDto findDongLocation(String entireCode);

	CompletableFuture<Void> aSyncCalcApartDealList(Map.Entry<Float, ArrayList<ApartDealDto>> e, List<ApartDealAreaDto> apartDealAreaDtoList) throws InterruptedException;
}
