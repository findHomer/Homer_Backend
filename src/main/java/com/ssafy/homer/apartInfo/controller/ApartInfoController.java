package com.ssafy.homer.apartInfo.controller;

import java.util.List;
import java.util.Map;

import com.ssafy.homer.apartInfo.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.apartInfo.service.ApartInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apartments")
public class ApartInfoController {
	private final ApartInfoService apartInfoService;
	
	/**
	 * 전체 아파트의 간단한 정보를 전달
	 * @return
	 */
	@GetMapping()
	public ResponseEntity totalApart() {
		
		List<ApartInfoDto> lists = apartInfoService.findTotalApart();
		return ResponseEntity.ok().body(lists);
	}
	
	@GetMapping("/locations/maps")
	public ResponseEntity apartListInMap(SearchMapDto searchMapDto) {
		//System.out.println(searchDto);
		List<ApartInfoDto> lists = apartInfoService.findApartInMap(searchMapDto);
		return ResponseEntity.ok().body(lists);
	}

	@GetMapping("/locations/names")
	public ResponseEntity apartListByName(SearchNameDto searchNameDto){

		List<ApartInfoDto> lists = apartInfoService.findApartByName(searchNameDto);
		return ResponseEntity.ok().body(lists);
	}

	@GetMapping("/details")
	public ResponseEntity apartDetails(@RequestParam String aptId){
		ApartInfoDetailDto apartInfoDetailDto =  apartInfoService.findApartDetail(aptId);

		return ResponseEntity.ok().body(apartInfoDetailDto);
	}
	
	@GetMapping("/locations/dong")
	public ResponseEntity mapLocation(@RequestParam String dongCode) {
		MapLocationDto location = apartInfoService.findDongLocation(dongCode);
		return ResponseEntity.ok().body(location);
	}
	
	@GetMapping("/rankings")
	public ResponseEntity getTopRanks(){
		List<RankingDto> ranking = apartInfoService.getTopRanks();
		return ResponseEntity.ok().body(ranking);
	}
}
