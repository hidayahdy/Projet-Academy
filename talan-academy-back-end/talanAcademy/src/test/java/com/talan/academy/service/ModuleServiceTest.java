package com.talan.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.talan.academy.entities.Cursus;
import com.talan.academy.entities.Module;
import com.talan.academy.helpers.ModelMapperConverter;
import com.talan.academy.repositories.ModuleRepository;
import com.talan.academy.services.ModuleService;

@SpringBootTest
class ModuleServiceTest {

	@Autowired
	private ModuleService moduleService;

	@MockBean
	private ModuleRepository moduleRepository;
	
	private Module module ; 
	
	private Module module2 ; 
	
	private Module module3 ; 
	
	@BeforeEach
	 void setUp() throws Exception {
		Cursus cursus = new Cursus() ; 
		cursus.setId(1L);
		
		 module = new Module(1L, "Algorithmie Et OO", "Ce module permet aux apprentis de comprendre les fondements de la programmation et l’algorithmie, savoir représenter un algorithme via des schémas et le pseudo-langage et compiler un programme en java. Ensuite, les apprentis découvrent les concepts de la programmation orienté objet et appliqueront ces fondements sur des exemples d''application en Java", cursus);
		 module2 = new Module(2L, "Algorithmie", "Ce module permet aux apprentis de comprendre les fondements de la programmation et l’algorithmie", cursus);
		 module3 = new Module(2L, "Algorithmie", "Ce module permet aux apprentis de comprendre les fondements de la programmation et l’algorithmie", cursus);	
	}
	
	@Test
	void testListModuleByCursusId() {
			List<Module> list = new ArrayList<>() ; 
			list.add(module) ; 
			list.add(module2) ; 
			list.add(module3);
			when(moduleRepository.findByCursusId(1L)).thenReturn(list) ; 
			List<Module> moduleList = ModelMapperConverter.mapAll(moduleService.getModuleByCursusId(1L), Module.class) ; 
			assertEquals(3, moduleList.size());
	}
}
