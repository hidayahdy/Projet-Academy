package com.talan.academy.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class FileControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void whenFileUploaded_thenVerifyStatus() throws Exception {

		MockMultipartFile sampleFile = new MockMultipartFile("file", "hello.txt", MediaType.MULTIPART_FORM_DATA_VALUE,
				"This is the file content".getBytes());

		MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders
				.multipart("/talan/files/upload");

		mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().isOk());
	}

}
