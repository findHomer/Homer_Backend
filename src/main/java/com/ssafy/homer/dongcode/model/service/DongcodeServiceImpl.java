package com.ssafy.homer.dongcode.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.homer.dongcode.model.domain.DongcodeEntity;
import com.ssafy.homer.dongcode.model.dto.DongcodeDto;
import com.ssafy.homer.dongcode.model.repository.DongcodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DongcodeServiceImpl implements DongcodeService{
	
	private final DongcodeRepository dongcodeRepository;

	@Override
	public List<DongcodeDto> getSidoList() throws Exception {
		
		List<DongcodeEntity> entities = dongcodeRepository.getNextList("__00000000");
		
		List<DongcodeDto> result = entities.stream()
		.map(m -> DongcodeDto.builder()
				.code(m.getSidoCode())
				.name(m.getSidoName())
				.build())
		.collect(Collectors.toList());
		
		return result;
	}

	@Override
	public List<DongcodeDto> getSigunguList(String sidoCode) throws Exception {
		
		List<DongcodeEntity> entities = dongcodeRepository.getNextList(sidoCode + "___00000");
		
		List<DongcodeDto> result = entities.stream()
				.filter(m -> !m.getSigunguCode().equals("000"))
		.map(m -> DongcodeDto.builder()
				.code(m.getSigunguCode())
				.name(m.getSigunguName())
				.build())
		.collect(Collectors.toList());

		return result;
	}

	@Override
	public List<DongcodeDto> getDongList(String sigunguCode) throws Exception {
		List<DongcodeEntity> entities = dongcodeRepository.getNextList(sigunguCode + "_____");
		
		List<DongcodeDto> result = entities.stream()
				.filter(m -> !m.getDongCode().equals("00000"))
				.map(m -> 
					DongcodeDto.builder()
						.code(m.getDongCode())
						.name(m.getDongName())
						.build()
				)
				.collect(Collectors.toList());

		return result;
	}
	
}
