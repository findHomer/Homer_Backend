package com.ssafy.homer.bookmark.domain;

import com.ssafy.homer.apartInfo.domain.ApartInfo;
import com.ssafy.homer.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity(name = "bookmark")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "apt_id")
    ApartInfo apartInfo;

}
