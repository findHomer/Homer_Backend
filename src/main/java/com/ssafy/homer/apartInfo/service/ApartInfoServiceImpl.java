package com.ssafy.homer.apartInfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.homer.apartInfo.dto.ApartInfoMapping;
import com.ssafy.homer.apartInfo.repository.ApartInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApartInfoServiceImpl implements ApartInfoService{
	
	private final ApartInfoRepository apartInfoRepository;
	@Override
	public List<ApartInfoMapping> getTotalApart() {
		// TODO Auto-generated method stub
		return apartInfoRepository.findAll();
	}

}
