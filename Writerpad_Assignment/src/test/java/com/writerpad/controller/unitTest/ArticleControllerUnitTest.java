package com.writerpad.controller.unitTest;

import static org.junit.Assert.assertEquals;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.writerpad.domain.Article;
import com.writerpad.repository.ArticleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
public class ArticleControllerUnitTest {

	private MockMvc mockMvc;

	@MockBean
	private ArticleRepository articleRepository;

	@Autowired(required = true)
	private WebApplicationContext wac;

	private Article article = new Article().toBuilder().body("Hello").description("How").title("you").favorited(false)
			.favoritesCount(2).build();
	private String articleJson = new Gson().toJson(article);

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void create() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/articles").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(articleJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void update() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/update").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(articleJson);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void deleteById() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/" + new ObjectId().toString());
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void getAll() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/all");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getById() throws Exception {

		String id = new ObjectId().toString();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/" + id);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
}
