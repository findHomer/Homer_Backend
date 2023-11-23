package com.ssafy.homer.bookmark.service;

import com.ssafy.homer.bookmark.domain.Bookmark;
import com.ssafy.homer.bookmark.dto.BookmarkDelDto;
import com.ssafy.homer.bookmark.dto.BookmarkDto;
import com.ssafy.homer.bookmark.dto.MyApartInfoDto;

import java.util.List;
public interface BookmarkService {
	public void deleteBookmark(String aptId);
	
	public void addBookmark(BookmarkDto bookmarkDto);

	public List<MyApartInfoDto> getBookmark();
}
