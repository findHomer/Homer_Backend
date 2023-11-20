package com.ssafy.homer.bookmark.service;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.repository.ApartInfoRepository;
import com.ssafy.homer.apartInfo.service.ApartInfoService;
import com.ssafy.homer.bookmark.domain.Bookmark;
import com.ssafy.homer.bookmark.dto.BookmarkDelDto;
import com.ssafy.homer.bookmark.dto.BookmarkDto;
import com.ssafy.homer.bookmark.repository.BookmarkRepository;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Security;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceImpl implements BookmarkService{

    private final ApartInfoRepository apartInfoRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public void deleteBookmark(BookmarkDelDto bookmarkDelDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow();
        //인증객체에 email이아닌 ID 넣을지 고려 할 필요성 있음

        Long bookmarkId = bookmarkDelDto.getBookmarkId();
        //본인이 체크한 북마크인지 확인
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow();
        if(bookmark.getUser().equals(user)){
            bookmarkRepository.delete(bookmark);
        }else{
            //본인아닐때 예외 처리
        }

    }

    @Override
    public void addBookmark(BookmarkDto bookmarkDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow();
        ApartInfo apartInfo = apartInfoRepository.findById(bookmarkDto.getAptId()).orElseThrow();

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .apartInfo(apartInfo)
                .build();

        bookmarkRepository.save(bookmark);
        //SQLIntegrityConstraintViolationException  키중복에러 발생가능

    }

    @Override
    public List<Bookmark> getBookmark() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow();

        return user.getBookmarkList();
    }
}
