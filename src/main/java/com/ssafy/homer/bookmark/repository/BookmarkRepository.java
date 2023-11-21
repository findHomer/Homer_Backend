package com.ssafy.homer.bookmark.repository;

import com.ssafy.homer.bookmark.domain.Bookmark;
import com.ssafy.homer.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

}
