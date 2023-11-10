package com.ssafy.homer.apartInfo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchDto;
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
		
		List<ApartInfoDto> lists = apartInfoService.getTotalApart();
		return ResponseEntity.ok().body(lists);
	}
	
	@GetMapping("/locations")
	public ResponseEntity apartInMap(SearchDto searchDto) {
		//System.out.println(searchDto);
		List<ApartInfoDto> lists = apartInfoService.getApartInMap(searchDto);
		return ResponseEntity.ok().body(lists);
	}
	
	
}
