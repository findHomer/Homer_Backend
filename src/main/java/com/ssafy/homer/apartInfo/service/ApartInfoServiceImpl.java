package com.ssafy.homer.apartInfo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ssafy.homer.apartInfo.domain.ApartDeal;
import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.dto.*;
import com.ssafy.homer.exception.BaseException;
import com.ssafy.homer.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssafy.homer.apartInfo.repository.ApartInfoRepository;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApartInfoServiceImpl implements ApartInfoService{
	
	private final ApartInfoRepository apartInfoRepository;
	private final ApplicationContext context;

	private static final Logger logger = LoggerFactory.getLogger(ApartInfoService.class);


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
		return apartInfoRepository.searchName(searchNameDto);
	}

	@Override
	public ApartInfoDetailDto findApartDetail(String apartId) {
		 ApartInfo apartInfo =  apartInfoRepository.findById(apartId).orElseThrow(() -> new BaseException(ErrorCode.APART_NOT_FOUND));
		 System.out.println(apartInfo.getParkPerHouse());
		 //전용면적을 key로 apartDealDto를 넣어줌
		TreeMap<Float,ArrayList<ApartDealDto>> apartTransactionInfo = new TreeMap<>();

		//전용면적 기준 데이터 정리
		divideDataByArea(apartTransactionInfo,apartInfo);

		//전용면적별 아파트 거래내역 및 평균 계산
		List<ApartDealAreaDto> apartDealAreaDtoList = new ArrayList<ApartDealAreaDto>();
		//calcApartDealList(apartDealAreaDtoList,apartTransactionInfo);

		/**
		//비동기방식
		int processors = Runtime.getRuntime().availableProcessors();
		int threadPoolSize = Math.max(2,processors);
		ExecutorService customThreadPool = Executors.newFixedThreadPool(threadPoolSize);
		 **/

		ApartInfoService proxy = context.getBean(ApartInfoService.class);
		// 비동기 작업 리스트
		List<CompletableFuture<Void>> futures = apartTransactionInfo.entrySet().stream()
						.map(entry -> {
							try {
								return proxy.aSyncCalcApartDealList(entry, apartDealAreaDtoList);
							} catch (InterruptedException e) {
								throw new RuntimeException(e);
							}
						})
						.collect(Collectors.toList());

		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
		allOf.join();  // 모든 작업이 완료될 때까지 기다림

		apartDealAreaDtoList.sort((o1,o2)-> (int)(o1.getExclusiveArea()-o2.getExclusiveArea()));

		ApartInfoDetailDto apartInfoDetailDto = ApartInfoDetailDto.builder()
				 .aptId(apartInfo.getAptId())
				 .aisleType(apartInfo.getAisleType())
				 .allowDate(apartInfo.getAllowDate())
				 .aptName(apartInfo.getAptName())
				 .parkPerHouse(apartInfo.getParkPerHouse())//소수점자릿수 체크
				 .lawAddr(apartInfo.getLawAddr())
				 .roadAddr(apartInfo.getRoadAddr())
				 .emails(new ArrayList<String>())
				 .dongCount(apartInfo.getDongCount())
				 .maxFloor(apartInfo.getMaxFloor())
				 .lat(apartInfo.getLat())
				 .lng(apartInfo.getLng())
				 .householdCount(apartInfo.getHouseholdCount())
				 .dealInfos(apartDealAreaDtoList)
				 .build();

		return apartInfoDetailDto;
	}


	public void calcApartDealList(List<ApartDealAreaDto> apartDealAreaDtoList,TreeMap<Float,ArrayList<ApartDealDto>> apartTransactionInfo){
		LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

		for(Map.Entry<Float,ArrayList<ApartDealDto>> e: apartTransactionInfo.entrySet()){
			Map<String, MonthlyData> monthlyDataMap = new HashMap<>();
			for(ApartDealDto deal: e.getValue()) {
				LocalDate transactionDate = deal.getTransactionDate();

				// 최근 3년 데이터만 처리
				if (transactionDate.isAfter(threeYearsAgo)) {
					String monthYearKey = transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
					MonthlyData monthlyData = monthlyDataMap.getOrDefault(monthYearKey, new MonthlyData(0,0));

					monthlyData.addDeal(Integer.parseInt(deal.getTransactionAmount().replace(",", "")));
					monthlyDataMap.put(monthYearKey, monthlyData);
				}

			}
			ArrayList<AverageMonthDto> averageMonthDtoList = new ArrayList<>();
			LocalDate startDate = LocalDate.now().minusYears(3);
			LocalDate endDate = LocalDate.now();

			while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {

				averageMonthDtoList.add(new AverageMonthDto(startDate.format(DateTimeFormatter.ofPattern("yy.MM")),monthlyDataMap.getOrDefault(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM")),new MonthlyData(0,0)).getAverage()));
				startDate = startDate.plusMonths(1);
			}
			apartDealAreaDtoList.add(new ApartDealAreaDto(e.getKey(),e.getValue(),averageMonthDtoList));
		}

	}

	@Async("taskExecutor")
	public CompletableFuture<Void> aSyncCalcApartDealList(Map.Entry<Float,ArrayList<ApartDealDto>> e, List<ApartDealAreaDto> apartDealAreaDtoList) throws InterruptedException {
		logger.info("Async method started. Thread: " + Thread.currentThread().getName());
		LocalDate threeYearsAgo = LocalDate.now().minusYears(3);

		Map<String, MonthlyData> monthlyDataMap = new HashMap<>();
		for(ApartDealDto deal: e.getValue()) {
			LocalDate transactionDate = deal.getTransactionDate();

			// 최근 3년 데이터만 처리
			if (transactionDate.isAfter(threeYearsAgo)) {
				String monthYearKey = transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
				MonthlyData monthlyData = monthlyDataMap.getOrDefault(monthYearKey, new MonthlyData(0,0));

				monthlyData.addDeal(Integer.parseInt(deal.getTransactionAmount().replace(",", "")));
				monthlyDataMap.put(monthYearKey, monthlyData);
			}

		}
		ArrayList<AverageMonthDto> averageMonthDtoList = new ArrayList<>();
		LocalDate startDate = LocalDate.now().minusYears(3);
		LocalDate endDate = LocalDate.now();

		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {

			averageMonthDtoList.add(new AverageMonthDto(startDate.format(DateTimeFormatter.ofPattern("yy.MM")),monthlyDataMap.getOrDefault(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM")),new MonthlyData(0,0)).getAverage()));
			startDate = startDate.plusMonths(1);
		}
		apartDealAreaDtoList.add(new ApartDealAreaDto(e.getKey(),e.getValue(),averageMonthDtoList));
		//System.out.println("hello");
		logger.info("Async method finished. Thread: " + Thread.currentThread().getName());

		return CompletableFuture.completedFuture(null);
	}

	public void divideDataByArea(Map<Float,ArrayList<ApartDealDto>> map,ApartInfo apartInfo){
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
	private int totalAmount ;
	private int count ;

	public MonthlyData(int totalAmount,int count){
		this.totalAmount = totalAmount;
		this.count = count;
	}

	public void addDeal(int amount) {
		totalAmount += amount;
		count++;
	}

	public float getAverage() {
		return count == 0 ? 0 : (float) totalAmount / count;
	}
}
