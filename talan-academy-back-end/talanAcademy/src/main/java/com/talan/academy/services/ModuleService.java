package com.talan.academy.services;

import java.util.List;

import com.talan.academy.dto.ModuleDto;

public interface ModuleService {

	List<ModuleDto> getModuleByCursusId(Long id) ;
}
