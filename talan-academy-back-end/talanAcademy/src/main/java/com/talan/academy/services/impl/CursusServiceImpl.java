package com.talan.academy.services.impl;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.talan.academy.dto.CursusDto;
import com.talan.academy.entities.Cursus;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.CursusRepository;
import com.talan.academy.services.CursusService;
import com.talan.academy.services.FileService;
import com.talan.academy.services.ModuleService;

@Service
public class CursusServiceImpl implements CursusService {
	private final CursusRepository cursusRepository;
	private final ModuleService moduleService;
	private final FileService fileService;
	
	public CursusServiceImpl(CursusRepository cursusRepository,
			ModuleService moduleService, FileServiceImpl fileServiceImpl) {
		this.cursusRepository = cursusRepository;
		this.moduleService = moduleService;
		this.fileService= fileServiceImpl;
	}
	@Override
	public List<CursusDto> getAllCursus() {
		return ModelMapperConverter.mapAll(cursusRepository.findAll(), CursusDto.class);}
	@Override
	public CursusDto findCursusById(Long id) {
		Optional<Cursus> cursus = cursusRepository.findById(id);

		if (!cursus.isPresent()) return null;
		CursusDto cursusDto = ModelMapperConverter.map(cursus, CursusDto.class) ;		
		cursusDto.setModuleDto(moduleService.getModuleByCursusId(id));
		return cursusDto ;
	}
	
	@Override
	public List<CursusDto> getAllCursusByTypeAndVisible(EcursusType type, Boolean visible) {
		return ModelMapperConverter.mapAll(cursusRepository.findByTypeAndVisible(type, visible), CursusDto.class);
	}
	
	@Override
	public CursusDto addCursus(CursusDto cursusDto, MultipartFile cursusPicture) throws IOException {
		cursusPicture.getOriginalFilename();
		String pictureName = fileService.save(cursusPicture);
		cursusDto.setPicture(pictureName);
		return ModelMapperConverter.map(cursusRepository.save(ModelMapperConverter.map(cursusDto, Cursus.class)), cursusDto);
	}
	@Override
	@Transactional
	public CursusDto updateCursusVisible(Long id, boolean visible) {
		Optional<Cursus> cursusRes = cursusRepository.findById(id);
		if(!cursusRes.isPresent()) return null;
		Cursus cursus = cursusRes.get(); 
		cursus.setVisible(visible);
		return ModelMapperConverter.map(cursus, CursusDto.class);
	}
	@Override
	public void deleteCursus(Long id) {
		cursusRepository.deleteById(id);	
	}
	@Override
	public CursusDto updateCursus(CursusDto cursusDto) {
		Optional<Cursus>  cursusRes = cursusRepository.findById(cursusDto.getId());
		if(!cursusRes.isPresent()) return null;
		Cursus cursus = cursusRes.get();
		ModelMapperConverter.map(cursusDto, cursus);
		return ModelMapperConverter.map(cursusRepository.save(cursus), CursusDto.class);
	}
	@Override
	public Page<CursusDto> getAllCursusByPagination(int page, int size) {
		PageRequest pr = PageRequest.of(page, size).withSort(org.springframework.data.domain.Sort.by("creationDate").descending());
		return cursusRepository.findAll(pr).map(entity -> ModelMapperConverter.map(entity, CursusDto.class));
	}
	
	
	
	
	

}
