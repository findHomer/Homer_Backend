package com.ssafy.homer.apartInfo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.ssafy.homer.apartInfo.domain.ApartDeal;
import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.dto.*;
import com.ssafy.homer.bookmark.domain.Bookmark;
import com.ssafy.homer.exception.BaseException;
import com.ssafy.homer.exception.ErrorCode;
import com.ssafy.homer.user.domain.MyUserDetail;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.homer.apartInfo.repository.ApartInfoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApartInfoServiceImpl implements ApartInfoService{
	
	private final ApartInfoRepository apartInfoRepository;
	@Override
	public List<ApartInfoDto> findTotalApart() {
		return apartInfoRepository.findSimpleAll();
	}
	
	@Override
	public List<ApartInfoDto> findApartInMap(SearchMapDto searchMapDto) {
		
		return apartInfoRepository.searchMap(searchMapDto);
	}

	@Override
	public List<ApartInfoDto> findApartByName(SearchNameDto searchNameDto) {
		System.out.println(searchNameDto);
		return apartInfoRepository.searchName(searchNameDto);
	}

	@Override
	public ApartInfoDetailDto findApartDetail(String apartId) {
		 ApartInfo apartInfo =  apartInfoRepository.findById(apartId).orElseThrow(() -> new BaseException(ErrorCode.APART_NOT_FOUND));

		 Boolean mark=false;
		 //userId로 토큰 저장값 바꾸기

		List<String> emails = new ArrayList<>();

		for(Bookmark bookmark: apartInfo.getBookmarkList()) {
			emails.add(bookmark.getUser().getEmail());
		}

		 List<ApartDealAreaDto> apartDealAreaDtoList = new ArrayList<ApartDealAreaDto>();

		 //전용면적을 key로 apartDealDto를 넣어줌
		TreeMap<Float,ArrayList<ApartDealDto>> map = new TreeMap<>();
		for(ApartDeal deal: apartInfo.getApartDealList()){
			ArrayList<ApartDealDto> arr = map.getOrDefault(deal.getExclusiveArea(),new ArrayList<ApartDealDto>());

			arr.add(ApartDealDto.builder()
					.dealId(deal.getDealId())
					.floor(deal.getFloor())
					.transactionAmount(deal.getTransactionAmount().trim())
					.transactionDate(deal.getTransactionDate())
					.build());

			map.put(deal.getExclusiveArea(),arr);
		}
		LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

		for(Map.Entry<Float,ArrayList<ApartDealDto>> e: map.entrySet()){
			Map<String, MonthlyData> monthlyDataMap = new HashMap<>();
			for(ApartDealDto deal: e.getValue()) {
				LocalDate transactionDate = deal.getTransactionDate();

				// 최근 3년 데이터만 처리
				if (transactionDate.isAfter(threeYearsAgo)) {
					String monthYearKey = transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
					MonthlyData monthlyData = monthlyDataMap.getOrDefault(monthYearKey, new MonthlyData());

					monthlyData.addDeal(Integer.parseInt(deal.getTransactionAmount().replace(",", "")));
					monthlyDataMap.put(monthYearKey, monthlyData);
				}

			}
			ArrayList<AverageMonthDto> averageMonthDtos = new ArrayList<>();
			LocalDate startDate = LocalDate.now().minusYears(3);
			LocalDate endDate = LocalDate.now();

			while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {

				averageMonthDtos.add(new AverageMonthDto(startDate.format(DateTimeFormatter.ofPattern("yy.MM")),monthlyDataMap.getOrDefault(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM")),new MonthlyData()).getAverage()));
				startDate = startDate.plusMonths(1);
			}
			apartDealAreaDtoList.add(new ApartDealAreaDto(e.getKey(),e.getValue(),averageMonthDtos));
		}

		//
		 ApartInfoDetailDto apartInfoDetailDto = ApartInfoDetailDto.builder()
				 .aptId(apartInfo.getAptId())
				 .aisleType(apartInfo.getAisleType())
				 .allowDate(apartInfo.getAllowDate())
				 .aptName(apartInfo.getAptName())
				 .parkPerHouse(apartInfo.getParkPerHouse())//소수점자릿수 체크
				 .lawAddr(apartInfo.getLawAddr())
				 .roadAddr(apartInfo.getRoadAddr())
				 .dongCount(apartInfo.getDongCount())
				 .maxFloor(apartInfo.getMaxFloor())
				 .lat(apartInfo.getLat())
				 .lng(apartInfo.getLng())
				 .householdCount(apartInfo.getHouseholdCount())
				 .emails(emails)
				 .dealInfos(apartDealAreaDtoList)
				 .build();

		return apartInfoDetailDto;
	}

	@Override
	public MapLocationDto findDongLocation(String entireCode) {
		ApartInfo apart = apartInfoRepository.findFirstByEntireCodeStartingWith(entireCode).orElse(
				apartInfoRepository.findFirstByEntireCodeStartingWith(entireCode.substring(0,entireCode.length()-4)).orElseThrow(null));
		return MapLocationDto.builder()
				.lat(apart.getLat())
				.lng(apart.getLng())
				.build();
	}



}
class MonthlyData {
	// 월별 데이터 집계 클래스
	private int totalAmount = 0;
	private int count = 0;

	public void addDeal(int amount) {
		totalAmount += amount;
		count++;
	}

	public float getAverage() {
		return count == 0 ? 0 : (float) totalAmount / count;
	}
}
