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

    @Override      //���� ���н� ���⼭���� �۵�
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
    throws IOException, ServletException
    {   //getParameter �޼ҵ�� �� �������� ���� ���ؼ� ������ �����Ѵ�.
        String loginid = request.getParameter("j_username");
        String errormsg = "";
        
        // ������ ������ ���Ͽ� �ش� ���������� �޼����� �߼��Ѵ�.
        if (exception instanceof BadCredentialsException) {
            loginFailureCount(loginid);
            errormsg = "���̵� ��й�ȣ�� ���� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            loginFailureCount(loginid);
            errormsg = "���̵� ��й�ȣ�� ���� �ʽ��ϴ�. �ٽ� Ȯ�����ּ���.";
        } else if (exception instanceof DisabledException) {
            errormsg = "������ ��Ȱ��ȭ�Ǿ����ϴ�. �����ڿ��� �����ϼ���.";
        } else if (exception instanceof CredentialsExpiredException) {
            errormsg = "��й�ȣ ��ȿ�Ⱓ�� ���� �Ǿ����ϴ�. �����ڿ��� �����ϼ���.";
        } //���� �޼����� �ʹ� �ڼ��ϰ� �����ϸ� ��Ŀ���� ������ ������ �� �� �ִ�.
    
        request.setAttribute("username",  loginid);
        request.setAttribute("error_message",  errormsg);
        
        request.getRequestDispatcher("/loginForm?error").forward(request,  response);
    }

    // ��й�ȣ�� 3�� �̻� Ʋ�� �� ���� ��� ó��
    protected void loginFailureCount(String username) {
        /*
        // Ʋ�� Ƚ�� ������Ʈ
        userDao.countFailure(username);
        // Ʋ�� Ƚ�� ��ȸ
        int cnt = userDao.checkFailureCount(username);
        if(cnt==3) {
            // ���� ��� ó��
            userDao.disabledUsername(username);
        }
        */
    }
    
}