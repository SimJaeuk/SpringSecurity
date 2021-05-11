package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration               //이 클래스를 스프링 설정으로 사용하겠다는 의미
@EnableWebSecurity           // 스프링 시큐리티 기능을 활성화 하겠다는 의미
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override                // url 요청에 대한 허용여부설정, 윗부분이 가장 넓은 범위
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() //루트 url 요청에 대한 허용범위
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll() 
                .antMatchers("/guest/**").permitAll() //guest 아래 모든 요청 허용
                .antMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
 
        http.formLogin()        //로그인 폼의 url
                .loginPage("/loginForm")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/loginError")       //로그인 실패할 경우 호출
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll();
 
        http.logout()
        .logoutUrl("/logout") // default
        .logoutSuccessUrl("/") // 로그 아웃 후 떨어질 위치
        .permitAll();
        
     // 개발중에는 꺼 놓는다.
        http.csrf().disable();
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
            .and()
            .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
            // ROLE_ADMIN 에서 ROLE_는 자동으로 붙는다.
    }
    
    // passwordEncoder() 추가
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
}