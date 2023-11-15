package com.ssafy.homer.dongcode.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.dongcode.model.dto.DongcodeDto;
import com.ssafy.homer.dongcode.model.service.DongcodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dongcode")
public class DongcodeController {
	
	private final DongcodeService dongcodeService;
	
	@GetMapping("/sido")
	ResponseEntity<?> getSidoList() throws Exception{
		List<DongcodeDto> result = dongcodeService.getSidoList();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{sidoCode}/sigungu")
	ResponseEntity<?> getSigunguList(@PathVariable(name = "sidoCode", required = true) String sidoCode) throws Exception{
		List<DongcodeDto> result = dongcodeService.getSigunguList(sidoCode);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{sigunguCode}/dong")
	ResponseEntity<?> getDongList(@PathVariable(name = "sigunguCode", required = true) String sigunguCode) throws Exception{
		List<DongcodeDto> result = dongcodeService.getDongList(sigunguCode);
		return ResponseEntity.ok(result);
	}
}
