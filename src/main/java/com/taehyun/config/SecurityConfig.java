package com.taehyun.config;

import com.taehyun.jwt.JwtAuthenticationFilter;
import com.taehyun.jwt.JwtService;
import com.taehyun.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())               // CSRF 비활성화 (JWT 사용할 거라서)
                .formLogin(form -> form.disable())      // 기본 로그인 페이지 비활성화
                .httpBasic(basic -> basic.disable())    // HTTP Basic 인증 비활성화
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))     // h2-console 프레임 깨지는 거 방지
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth       // URL별 접근 권한 설정
                        .requestMatchers("/login", "/signup", "/error").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, MemberRepository memberRepository) {
        return new JwtAuthenticationFilter(jwtService, memberRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
