package com.ssafy.homer.aptreview.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = {
				"spring.config.location=classpath:application-aptreview-test.yaml"
		}
		)
@Slf4j
class AptReviewControllerTest {

	@Value(value="aptId")
	String aptId;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("### 리뷰 등록 테스트 ###")
	void testRegister() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("### 리뷰 리스트 불러오기 테스트 ###")
	void testList() throws Exception {
		log.debug("### 리뷰 리스트 불러오기 테스트 ###");
		mockMvc.perform(get("/api/v1/review/{aptId}",aptId))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(print());
	}

	@Test
	@DisplayName("### 리뷰 등록 테스트 ###")
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("### 리뷰 등록 테스트 ###")
	void testModify() {
		fail("Not yet implemented");
	}

}
