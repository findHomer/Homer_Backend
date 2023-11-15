package com.ssafy.homer.dongcode.model.service;

import java.util.List;

import com.ssafy.homer.dongcode.model.dto.DongcodeDto;

public interface DongcodeService {
	List<DongcodeDto> getSidoList() throws Exception;
	List<DongcodeDto> getSigunguList(String sidoCode) throws Exception;
	List<DongcodeDto> getDongList(String sigunguCode) throws Exception;
}
