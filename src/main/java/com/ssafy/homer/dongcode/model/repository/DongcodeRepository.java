package com.ssafy.homer.dongcode.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.dongcode.model.domain.DongcodeEntity;

@Repository
public interface DongcodeRepository extends JpaRepository<DongcodeEntity, String> {
	@Query(nativeQuery = true, value = "select * from dongcode where entire_code like :likeString")
	public List<DongcodeEntity> getNextList(@Param("likeString") String likeString);
}
