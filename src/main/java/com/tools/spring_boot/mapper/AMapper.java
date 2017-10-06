package com.tools.spring_boot.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tools.spring_boot.domain.AEntity;

@Repository
public interface AMapper {

	List<AEntity> selectAll();
}
