package com.talan.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.talan.academy.dto.CursusDto;
import com.talan.academy.entities.Cursus;
import com.talan.academy.enums.EcursusType;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.CursusRepository;
import com.talan.academy.services.ModuleService;
import com.talan.academy.services.impl.CursusServiceImpl;
import com.talan.academy.services.impl.FileServiceImpl;

@ExtendWith(MockitoExtension.class)
class CursusServiceImplTest {

	@Mock
	CursusRepository cursusRepository;

	@Mock
	FileServiceImpl fileService;

	@Mock
	ModuleService moduleService;

	@InjectMocks
	CursusServiceImpl cursusService;

	
	@Test
	void getAllCursus_success() throws Exception {
		Cursus records1 = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Cursus records2 = new Cursus(2L, "Angular", "java-cursus.png", "cursus angular with talan academy", true,
				EcursusType.PUBLIC, null);
		List<Cursus> cursusList = new ArrayList<>(Arrays.asList(records1, records2));
		Mockito.when(cursusRepository.findAll()).thenReturn(cursusList);
		List<CursusDto> cursusSer = cursusService.getAllCursus();
		assertEquals(2, cursusSer.size());
		verify(cursusRepository, times(1)).findAll();
	}
	 

	@Test
	void getCursusById_success() throws Exception {

		Cursus records11 = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		lenient().when(cursusRepository.findById(1L)).thenReturn(Optional.of(records11));
		CursusDto cursusDto = cursusService.findCursusById(1L);
		assertEquals(records11.getName(), cursusDto.getName());
		assertEquals(records11.getDescription(), cursusDto.getDescription());
	}
	
	@Test
	void getCursusByIdNotSuccess() throws Exception {

		when(cursusRepository.findById(1L)).thenReturn(Optional.empty());
		CursusDto cursusDto = cursusService.findCursusById(1L);
		assertNull(cursusDto);
	}



	@Test
	void updateCursusVisible_success() throws Exception {

		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		
		Mockito.when(cursusRepository.findById(cursus.getId())).thenReturn(Optional.of(cursus));
		CursusDto cursusUpdated = cursusService.updateCursusVisible(cursus.getId(), false);
		assertEquals(cursus.getId(), cursusUpdated.getId());
		assertEquals(cursus.isVisible(), cursusUpdated.isVisible());

	}
	
	@Test
	void updateCursusVisibleNotSuccess() throws Exception {

		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Mockito.when(cursusRepository.findById(cursus.getId())).thenReturn(Optional.empty());
		CursusDto cursusUpdated = cursusService.updateCursusVisible(cursus.getId(), false);
		assertNull(cursusUpdated);

	}

	@Test
	void updateCursus_success() throws Exception {

		Cursus cursus = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Cursus cursusUpdatedExpected = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy",
				false, EcursusType.PUBLIC, null);
		CursusDto cursusDto = ModelMapperConverter.map(cursusUpdatedExpected, CursusDto.class);
		Mockito.when(cursusRepository.findById(cursus.getId())).thenReturn(Optional.of(cursus));
		Mockito.when(cursusRepository.save(cursusUpdatedExpected)).thenReturn(cursusUpdatedExpected);
		CursusDto cursusUpdated1 = cursusService.updateCursus(cursusDto);
		assertEquals(cursusUpdatedExpected.getId(), cursusUpdated1.getId());
	}

	@Test
	 void getAllCursusWithPagination_success() throws Exception {
		Cursus records1 = new Cursus(1L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		Cursus records2 = new Cursus(2L, "Angular", "angular.png", "cursus angular with talan academy", true,
				EcursusType.PUBLIC, null);
		Cursus records3 = new Cursus(3L, "Angular-react", "angular-react.png", "cursus angular with talan academy",
				true, EcursusType.PUBLIC, null);

		List<Cursus> cursusList = new ArrayList<>(Arrays.asList(records1, records2, records3));

		Pageable pageable = PageRequest.of(0, 3).withSort(Sort.by("creationDate").descending());
		Page<Cursus> page = new PageImpl<>(cursusList, pageable, cursusList.size());

		when(cursusRepository.findAll(pageable)).thenReturn(page);
		Page<CursusDto> expectedList = cursusService.getAllCursusByPagination(0, 3);

		assertEquals(page.getSize(), expectedList.getSize());
		verify(cursusRepository, times(1)).findAll(pageable);

	}

	@Test
	void deleteCursus_success() throws Exception {

		Cursus cursus = new Cursus(3L, "java", "java-cursus.png", "cursus java with talan academy", true,
				EcursusType.PUBLIC, null);
		cursusService.deleteCursus(cursus.getId());
		verify(cursusRepository).deleteById(cursus.getId());

	}

	@Test
	void updateCursusVisible() throws Exception {
		Cursus cursus = new Cursus(3L, "java", "java-cursus.png", "cursus java with talan academy", false,
				EcursusType.PUBLIC, null);
		Mockito.when(cursusRepository.findById(3L)).thenReturn(Optional.of(cursus));
		CursusDto cursusDto = cursusService.updateCursusVisible(3L, true);
		assertEquals(true,cursusDto.isVisible());
		assertEquals(3L,cursusDto.getId());
		verify(cursusRepository, times(1)).findById(3L);
	}

	@Test
	void updateCursus() throws Exception {
		Cursus cursus = new Cursus(3L, "java", "java-cursus.png", "cursus java with talan academy", false,
				EcursusType.PUBLIC, null);
		CursusDto cursusDto = new CursusDto(3L, "java", "java-cursus.png", "cursus java with talan academy", false,
				EcursusType.PUBLIC, null, null);
		Mockito.when(cursusRepository.findById(3L)).thenReturn(Optional.of(cursus));
		Mockito.when(cursusRepository.save(cursus)).thenReturn(cursus);
		CursusDto result = cursusService.updateCursus(cursusDto);
		assertEquals(cursusDto, result);
		verify(cursusRepository, times(1)).findById(3L);
		verify(cursusRepository, times(1)).save(cursus);
	}
	
	@Test
	void updateCursusByIdNotFound() throws Exception {
		Cursus cursus = new Cursus(3L, "java", "java-cursus.png", "cursus java with talan academy", false,
				EcursusType.PUBLIC, null);
	
		Mockito.when(cursusRepository.findById(3L)).thenReturn(Optional.empty());
		CursusDto result = cursusService.updateCursus(ModelMapperConverter.map(cursus, CursusDto.class) );
		assertNull(result);
	}

	/*
	 * @Test void findAllCursusWithPaginationTest() {
	 * 
	 * CursusDto cursus = new CursusDto(3L, "java", "java-cursus.png",
	 * "cursus java with talan academy", false, EcursusType.PUBLIC, null, null);
	 * 
	 * List<CursusDto> list = new ArrayList<CursusDto>(); list.add(Cursus); Pageable
	 * pageable = PageRequest.of(0,
	 * 3).withSort(Sort.by("creationDate").descending()); Page<Cursus> page = new
	 * PageImpl<>(list, pageable, list.size());
	 * 
	 * when(cursusRepository.findAll(pageable)).thenReturn(page); List<CursusDto>
	 * empList = cursusService.getAllCursusByPagination(0, 3).getContent();
	 * 
	 * // then assertEquals(list.size(), empList.size()); assertEquals(list,
	 * empList); verify(cursusRepository, times(1)).findAll(pageable); }
	 */
	
	

}
