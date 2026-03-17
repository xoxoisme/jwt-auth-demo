package com.taehyun.entity;

import com.taehyun.MemberType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 65, unique = true, nullable = false)   // 중복 안됨
    private String email;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private MemberType type = MemberType.USER;
}
