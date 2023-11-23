package com.ssafy.homer.bookmark.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.apartInfo.repository.ApartInfoRepository;
import com.ssafy.homer.bookmark.domain.Bookmark;
import com.ssafy.homer.bookmark.dto.BookmarkDto;
import com.ssafy.homer.bookmark.dto.MyApartInfoDto;
import com.ssafy.homer.bookmark.repository.BookmarkRepository;
import com.ssafy.homer.user.domain.User;
import com.ssafy.homer.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceImpl implements BookmarkService{

    private final ApartInfoRepository apartInfoRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    @Override
    public void deleteBookmark(@RequestParam String aptId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow(null);
        //인증객체에 email이아닌 ID 넣을지 고려 할 필요성 있음

        //본인이 체크한 북마크인지 확인
        Bookmark bookmark = bookmarkRepository.findByUserUserIdAndApartInfoAptId(user.getUserId(),aptId).orElseThrow(null);

        bookmarkRepository.delete(bookmark);


    }

    @Override
    public void addBookmark(BookmarkDto bookmarkDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        User user = userRepository.findByEmail(email).orElseThrow(null);
        ApartInfo apartInfo = apartInfoRepository.findById(bookmarkDto.getAptId()).orElseThrow(null);

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .apartInfo(apartInfo)
                .build();

        bookmarkRepository.save(bookmark);
        //SQLIntegrityConstraintViolationException  키중복에러 발생가능

    }

    @Override
    public List<MyApartInfoDto> getBookmark() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(email).orElseThrow(null);
        List<MyApartInfoDto> aptList = new ArrayList<>();
        List<Bookmark> bookmarkList = user.getBookmarkList();
        
        for(Bookmark bookmark:bookmarkList) {
        	ApartInfo apt = bookmark.getApartInfo();
        	//aptList에 추가
        	aptList.add(MyApartInfoDto.builder()
        	.aptId(apt.getAptId())
        	.aptName(apt.getAptName())
        	.lat(apt.getLat())
        	.lng(apt.getLng())
        	.roadAddr(apt.getRoadAddr())
        	.build());
        }
        
        return aptList;
    }
}
