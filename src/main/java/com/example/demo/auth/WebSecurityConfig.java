package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration               //�� Ŭ������ ������ �������� ����ϰڴٴ� �ǹ�
@EnableWebSecurity           // ������ ��ť��Ƽ ����� Ȱ��ȭ �ϰڴٴ� �ǹ�
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public AuthenticationFailureHandler authenticationFailureHandler;

    @Override                // url ��û�� ���� ��뿩�μ���, ���κ��� ���� ���� ����
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() //��Ʈ url ��û�� ���� ������
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll() 
                .antMatchers("/guest/**").permitAll() //guest �Ʒ� ��� ��û ���
                .antMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
 
        http.formLogin() //�α��� ���� url
                .loginPage("/loginForm")
                .loginProcessingUrl("/j_spring_security_check")
                //.failureUrl("/loginForm?error")       //�α��� ������ ��� ȣ��
                .failureHandler(authenticationFailureHandler) // �츮�� ���� Ŭ������ ���� ó���� �ϰڴٰ� ����
                //.defaultSuccessUrl("/")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll();
 
        http.logout()
        .logoutUrl("/logout") // default
        .logoutSuccessUrl("/") // �α� �ƿ� �� ������ ��ġ
        .permitAll();
        
     // �����߿��� �� ���´�.
        http.csrf().disable();
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password(passwordEncoder().encode("1234")).roles("USER")
            .and()
            .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
            // ROLE_ADMIN ���� ROLE_�� �ڵ����� �ٴ´�.
    }
    
    // passwordEncoder() �߰�
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
}