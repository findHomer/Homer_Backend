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

	private BooleanExpression nameSearch(SearchNameDto searchNameDto) {

		return containsName(searchNameDto.getName())
				//.and(startWithDongCode(searchNameDto.getDongCode()))
				.and(eqAisleType(searchNameDto.getAisleType()))
				.and(goeHouseholdCount(searchNameDto.getHouseholdCount()));
	}


	private BooleanExpression mapSearch(SearchMapDto searchMapDto) {
		
		return betweenLat(searchMapDto.getStartLat(), searchMapDto.getEndLat())
				.and(betweenLng(searchMapDto.getStartLng(), searchMapDto.getEndLng()))
				.and(eqAisleType(searchMapDto.getAisleType()))
				.and(goeHouseholdCount(searchMapDto.getHouseholdCount()));
		//세대당 주차수
				
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


//	private BooleanExpression goeCanPark(Integer canPark,Integer householdCount){//세대당 주차수 따로 필드 만들 필요가있음
//		if(canPark==null||householdCount==null||householdCount==0){
//			return null;
//		}
//
//		return QApartInfo.apartInfo.
	//}
	private BooleanExpression eqApartName(String apartName) {
		if(!StringUtils.hasText(apartName)) {
			return null;
		}
		return QApartInfo.apartInfo.aptName.eq(apartName);
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
	
	//동코드
//	private BooleanExpression startWithDongCode(String dongCode) {
//		if(!StringUtils.hasText(dongCode)){
//			return null;
//		}
//		return QApartInfo.apartInfo.
//	}
	
	
	

}
