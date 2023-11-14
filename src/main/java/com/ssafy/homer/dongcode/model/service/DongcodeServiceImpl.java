package com.ssafy.homer.dongcode.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.homer.dongcode.model.repository.DongcodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DongcodeServiceImpl implements DongcodeService{
	
	private final DongcodeRepository dongcodeRepository;
	
}
