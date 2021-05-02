package com.imadelfetouh.moderatorservice.security;

import com.google.gson.Gson;
import com.imadelfetouh.moderatorservice.dal.ormmodel.Role;
import com.imadelfetouh.moderatorservice.jwt.ValidateJWTToken;
import com.imadelfetouh.moderatorservice.model.jwt.UserData;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("jwt-token")).findFirst().orElse(null);
            if(cookie == null) {
                httpServletResponse.setStatus(401);
                return;
            }
            else{
                String userData = ValidateJWTToken.getInstance().getUserData(cookie.getValue());
                Gson gson = new Gson();

                UserData u = gson.fromJson(userData, UserData.class);
                if(u.getRole().equals(Role.MODERATOR.name()) || u.getRole().equals(Role.ADMINISTRATOR.name())) {
                    httpServletRequest.setAttribute("userdata", userData);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
                else{
                    httpServletResponse.setStatus(401);
                    return;
                }
            }
        }

        httpServletResponse.setStatus(401);
    }
}
