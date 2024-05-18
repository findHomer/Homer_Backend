package com.ssafy.homer.bookmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.homer.bookmark.dto.BookmarkDto;
import com.ssafy.homer.bookmark.service.BookmarkService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = BookmarkController.class)
@Slf4j
public class AptBookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;


    @Test
    @DisplayName("### 아파트 북마크 지정 ###")
    @WithMockUser
    void postAptBookmark() throws Exception {
        log.debug("### 아파트 북마크 지정 ###");

        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setAptId("1");

        mockMvc.perform(post("/api/v1/bookmarks/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookmarkDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
