package com.ssafy.homer.apartInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.dto.ApartInfoMapping;
@Repository
public interface ApartInfoRepository extends JpaRepository<ApartInfo,String>{
	List<ApartInfoMapping> findAll();
}
