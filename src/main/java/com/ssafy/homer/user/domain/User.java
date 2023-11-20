package com.ssafy.homer.user.domain;

import com.ssafy.homer.bookmark.domain.Bookmark;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private LocalDate birth;

    private String role;

    private String userPhoto;


    @OneToMany(mappedBy ="user")
    private List<Bookmark> bookmarkList;



}
