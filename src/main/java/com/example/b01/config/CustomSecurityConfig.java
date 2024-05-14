package com.example.b01.config;

import com.example.b01.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)//사전 혹은 사후권한 체크
public class CustomSecurityConfig {

    private final DataSource dataSource;

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        log.info("----------configure-----------");

        httpSecurity.formLogin().loginPage("/member/login");
        //커스텀 로그인페이지 경로 수정
        httpSecurity.csrf().disable();
        //csrf 비활성화
        httpSecurity.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUserDetailService)
                .tokenValiditySeconds(60*60*24*30);

        //자동로그인을 위한 설정 변경
        return httpSecurity.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("----------web configure-------------");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        //css등은 스프링 시큐리티 적용받지 않게함 정적인것들

    }

    @Bean
    public PasswordEncoder passwordEncoder() {//패스워드 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


}

