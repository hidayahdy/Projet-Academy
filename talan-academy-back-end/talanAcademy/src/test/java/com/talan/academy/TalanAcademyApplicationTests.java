package com.talan.academy;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = TalanAcademyApplication.class)
class TalanAcademyApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithAnonymousUser
	void accessAllCursusWithoutAuthentification() throws Exception {
		mvc.perform(get("/talan/cursus/public")).andExpect(status().isOk());
	}

	@Test
	@WithAnonymousUser
	void accessAddNewApplicationWithoutAuthentification() throws Exception {
		mvc.perform(post("/talan/application")).andExpect(status().isUnauthorized());
	}

}
