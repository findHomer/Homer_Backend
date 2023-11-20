package com.ssafy.homer.bookmark.controller;

import com.ssafy.homer.bookmark.dto.BookmarkDelDto;
import com.ssafy.homer.bookmark.dto.BookmarkDto;
import com.ssafy.homer.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    @PostMapping()
    ResponseEntity addBookmark(@RequestBody BookmarkDto bookmarkDto){
        bookmarkService.addBookmark(bookmarkDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    ResponseEntity deleteBookmark(@RequestBody BookmarkDelDto bookmarkDelDto){
        bookmarkService.deleteBookmark(bookmarkDelDto);
        return ResponseEntity.ok().build();
    }
}
