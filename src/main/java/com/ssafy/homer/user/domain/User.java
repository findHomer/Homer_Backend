package com.ssafy.homer.user.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer userId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private LocalDate birth;

    private String role;

    private String userPhoto;

}
