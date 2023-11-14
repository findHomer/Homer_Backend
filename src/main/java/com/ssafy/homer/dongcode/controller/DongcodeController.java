package com.ssafy.homer.dongcode.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homer.dongcode.model.service.DongcodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DongcodeController {
	
	private final DongcodeService dongcodeService;
	
	
}
