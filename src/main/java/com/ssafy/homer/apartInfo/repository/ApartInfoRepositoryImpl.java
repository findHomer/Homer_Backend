package com.ssafy.homer.apartInfo.repository;

import java.util.List;

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
	
	private BooleanExpression mapSearch(SearchMapDto searchMapDto) {
		
		return betweenLat(searchMapDto.getStartLat(), searchMapDto.getEndLat())
				.and(betweenLng(searchMapDto.getStartLng(), searchMapDto.getEndLng()))
				.and(eqApartName(searchMapDto.getName()))
				.and(eqAisleType(searchMapDto.getAisleType()))
				.and(goeHouseholdCount(searchMapDto.getHouseholdCount()));
				
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
//	private BooleanExpression eqdongCode(Integer householdCount) {
//		if(householdCount==null) {
//			return null;
//		}
//		return QApartInfo.apartInfo
//	}
	
	
	

}
