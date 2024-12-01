package com.example.liveboard.global.jwt;

import com.example.liveboard.domain.user.dto.LoginRequestDTO;
import com.example.liveboard.global.userdetail.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;


public class JwtAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestDTO requestDTO = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequestDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getEmail(),
                            requestDTO.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        // 로그인시 기 발급된 토큰이 존재하면 해당 토큰 무효화 처리
        // AccessToken, RefreshToken Redis삭제
        jwtUtil.deleteToken(email);

        String accessToken = jwtUtil.createAccessToken(email);
        String refreshToken = jwtUtil.createRefreshToken(email);

        jwtUtil.saveJwt(accessToken, refreshToken, email);

        // RefreshToken 쿠키에 저장
        jwtUtil.addJwtToCookie("refreshToken", refreshToken, response);

        // AccessToken 쿠키에 저장
        jwtUtil.addJwtToCookie("accessToken", accessToken, response);

        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(" 200 : Ok");
        writer.println("로그인에 성공하였습니다.");
    }
}
