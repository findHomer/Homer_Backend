package com.ssafy.homer.aptreview.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.homer.aptreview.model.dto.ReviewDto;
import com.ssafy.homer.s3.util.S3Uploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.ssafy.homer.aptreview.model.service.AptReviewService;

import lombok.extern.slf4j.Slf4j;

@AutoConfigureMockMvc
@WebMvcTest(controllers = AptReviewController.class)
@Slf4j
class AptReviewControllerTest {

	@MockBean
	AptReviewService aptReviewService;

	@MockBean
	S3Uploader s3uploader;;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("### 리뷰 등록 테스트 ###")
	void testRegister() throws Exception {
		log.debug("### 리뷰 등록 테스트  ###");
		// 테스트에 사용할 데이터 생성
		ReviewDto review = new ReviewDto();
		// review 객체에 필요한 데이터 설정
		ObjectMapper objectMapper = new ObjectMapper();
		// 이미지 파일 생성
		byte[] imageContent = "test image content".getBytes();
		MockMultipartFile imageFile = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, imageContent);

		// 요청 본문 생성
		MockMultipartFile jsonFile = new MockMultipartFile("review", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(review));

		mockMvc.perform(multipart("/api/v1/review/")
						.file(imageFile)
						.file(jsonFile)
						.with(csrf()).with(user("jake").roles("USER")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(print());
	}



	@Test
	@DisplayName("### 리뷰 리스트 불러오기 테스트 ###")
	void testList() throws Exception {
		log.debug("### 리뷰 리스트 불러오기 테스트 ###");
		mockMvc.perform(get("/api/v1/review/{aptId}",1)
						.with(csrf()).with(user("jake").roles("USER")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andDo(print());
	}

	@Test
	@DisplayName("### 리뷰 삭제 테스트 ###")
	void testDelete() throws Exception {
		log.debug("### 리뷰 삭제 테스트 ###");
		mockMvc.perform(delete("/api/v1/review/")
						.param("reviewId", String.valueOf(1))
						.with(csrf()).with(user("jake").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
	}

	@Test
	@DisplayName("### 리뷰 변경 테스트 ###")
	void testModify() throws Exception {
		log.debug("### 리뷰 변경 테스트 ###");

		// 테스트에 사용할 데이터 생성
		ReviewDto review = new ReviewDto();

		mockMvc.perform(patch("/api/v1/review/")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(review))
				.with(csrf()).with(user("jake").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
	}

}
