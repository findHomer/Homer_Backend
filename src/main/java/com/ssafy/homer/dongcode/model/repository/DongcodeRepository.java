package com.ssafy.homer.dongcode.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.homer.dongcode.model.domain.DongcodeEntity;

@Repository
public interface DongcodeRepository extends JpaRepository<DongcodeEntity, String> {

}
