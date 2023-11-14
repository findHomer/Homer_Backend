package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import com.querydsl.core.types.Predicate;
import com.ssafy.homer.apartInfo.dto.SearchNameDto;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.homer.apartInfo.domain.QApartInfo;
import com.ssafy.homer.apartInfo.dto.ApartInfoDto;
import com.ssafy.homer.apartInfo.dto.SearchMapDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApartInfoRepositoryImpl implements ApartInfoRepositoryCustom{

	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<ApartInfoDto> searchMap(SearchMapDto searchMapDto) {
		QApartInfo  qApartInfo = QApartInfo.apartInfo;
		List<ApartInfoDto> result=  queryFactory
				.select(Projections.constructor(ApartInfoDto.class,qApartInfo.aptId,qApartInfo.aptName,qApartInfo.latitude,qApartInfo.longitude ))
				.from(qApartInfo)
				.where(mapSearch(searchMapDto)
						).fetch();
				
		
		return result;
	}

	@Override
	public List<ApartInfoDto> searchName(SearchNameDto searchNameDto) {
		QApartInfo  qApartInfo = QApartInfo.apartInfo;
		List<ApartInfoDto> result=  queryFactory
				.select(Projections.constructor(ApartInfoDto.class,qApartInfo.aptId,qApartInfo.aptName,qApartInfo.latitude,qApartInfo.longitude ))
				.from(qApartInfo)
				.where(nameSearch(searchNameDto)
				).fetch();


		return result;
	}

	/**
	 * 이름,동 기준으로 검색할때 부르는 쿼리
	 * @param searchNameDto
	 * @return
	 */
	private BooleanExpression nameSearch(SearchNameDto searchNameDto) {

		return containsName(searchNameDto.getName())
				.and(startWithDongCode(searchNameDto.getDongCode()))
				.and(eqAisleType(searchNameDto.getAisleType()))
				.and(goeParkPerHouse(searchNameDto.getParkPerHouse()))
				.and(goeHouseholdCount(searchNameDto.getHouseholdCount()));
	}


	/**
	 * 마우스로 지도 이동시킬때 부르는 쿼리
	 * @param searchMapDto
	 * @return
	 */
	private BooleanExpression mapSearch(SearchMapDto searchMapDto) {
		
		return betweenLat(searchMapDto.getStartLat(), searchMapDto.getEndLat())
				.and(betweenLng(searchMapDto.getStartLng(), searchMapDto.getEndLng()))
				.and(eqAisleType(searchMapDto.getAisleType()))
				.and(goeParkPerHouse(searchMapDto.getParkPerHouse()))
				.and(goeHouseholdCount(searchMapDto.getHouseholdCount()));
		
				
	}

	/**
	 * 아파트 이름이 포함되어있는지 확인
	 * @param name
	 * @return
	 */
	private BooleanExpression containsName(String name){
		return QApartInfo.apartInfo.aptName.contains(name);
	}
	
	/**
	 * 지도의 위도 사이에 있는 데이터 가져오기 
	 * @param startLat
	 * @param endLat
	 * @return
	 */
	private BooleanExpression betweenLat(Float startLat,Float endLat) {
		
		return QApartInfo.apartInfo.latitude.between(startLat, endLat);
	}
	
	/**
	 * 지도의 경도 사이에 있는 데이터 가져오기 
	 * @param startLat
	 * @param endLat
	 * @return
	 */
	private BooleanExpression betweenLng(Float startLng,Float endLng) {
			
			return QApartInfo.apartInfo.longitude.between(startLng, endLng);
		}

	/**
	 * 세대당 주차수 이상을 찾음
	 * @param parkPerHouse
	 * @return
	 */
	private BooleanExpression goeParkPerHouse(Float parkPerHouse){
		if(parkPerHouse==null||parkPerHouse==0.0){
			return null;
		}

		return QApartInfo.apartInfo.parkPerHouse.goe(parkPerHouse);
	}
	
	
	private BooleanExpression eqAisleType(String aisleType) {
		if(!StringUtils.hasText(aisleType)) {
			return null;
		}
		return QApartInfo.apartInfo.aisleType.eq(aisleType);
	}
	
	private BooleanExpression goeHouseholdCount(Integer householdCount) {
		if(householdCount==null) {
			return null;
		}
		return QApartInfo.apartInfo.householdCount.goe(householdCount);
	}
	
	//입력된 동코드로 시작하는지
	private BooleanExpression startWithDongCode(String dongCode) {
		if(!StringUtils.hasText(dongCode)){
			return null;
		}
		return QApartInfo.apartInfo.entireCode.startsWith(dongCode);
	}
	
	
	

}
