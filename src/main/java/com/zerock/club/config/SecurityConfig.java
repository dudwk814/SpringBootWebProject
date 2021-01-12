package com.zerock.club.config;

import com.zerock.club.security.filter.ApiCheckFilter;
import com.zerock.club.security.filter.ApiLoginFilter;
import com.zerock.club.security.handler.ApiLoginFailHandler;
import com.zerock.club.security.handler.ClubLoginSuccessHandler;
import com.zerock.club.security.service.ClubUserDetailsService;
import com.zerock.club.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*", jwtUtil());
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());

        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

        return apiLoginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests().antMatchers("/sample/all").permitAll() =
                .antMatchers("/sample/member").hasRole("USER");*/

        http.formLogin();   // 인증 / 인가에 문제 발생시 로그인 화면으로 리다이렉트
        http.csrf().disable();
        http.logout();

        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(userDetailsService);

        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // 로그인 성공 후 처리 핸들러
    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 사용자 계정은 user1
        auth.inMemoryAuthentication().withUser("user1")
        // 1111 패스워드 인코딩 결과
        .password("$2a$10$TxpzPsZiHV4SMH00jEuzRu1AEwuYrrYe6mqDWAsyMXxVMVKfjsJQO").roles("USER");
    }*/

}
