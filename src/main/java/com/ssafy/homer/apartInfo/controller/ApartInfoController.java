package com.ssafy.homer.apartInfo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.apartInfo.dto.ApartInfoMapping;
import com.ssafy.homer.apartInfo.service.ApartInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/apartments")
public class ApartInfoController {
	private final ApartInfoService apartInfoService;
	
	@GetMapping()
	public ResponseEntity totalApart() {
		
		List<ApartInfoMapping> lists = apartInfoService.getTotalApart();
		return ResponseEntity.ok().body(lists);
	}
	
}
