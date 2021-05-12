package com.example.demo.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override      //인증 실패시 여기서부터 작동
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
    throws IOException, ServletException
    {   //getParameter 메소드로 폼 데이터의 값을 구해서 변수에 저장한다.
        String loginid = request.getParameter("j_username");
        String errormsg = "";
        
        // 에러의 내용을 비교하여 해당 에러에대한 메세지를 발송한다.
        if (exception instanceof BadCredentialsException) {
            loginFailureCount(loginid);
            errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            loginFailureCount(loginid);
            errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof DisabledException) {
            errormsg = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
        } else if (exception instanceof CredentialsExpiredException) {
            errormsg = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
        } //에러 메세지를 너무 자세하게 제공하면 해커한테 유리한 정보가 될 수 있다.
    
        request.setAttribute("username",  loginid);
        request.setAttribute("error_message",  errormsg);
        
        request.getRequestDispatcher("/loginForm?error").forward(request,  response);
    }

    // 비밀번호를 3번 이상 틀릴 시 계정 잠금 처리
    protected void loginFailureCount(String username) {
        /*
        // 틀린 횟수 업데이트
        userDao.countFailure(username);
        // 틀린 횟수 조회
        int cnt = userDao.checkFailureCount(username);
        if(cnt==3) {
            // 계정 잠금 처리
            userDao.disabledUsername(username);
        }
        */
    }
    
}