package com.kosa.mapbegood.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.mapbegood.domain.member.dto.MemberLoginDTO;
import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.security.refresh.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberLoginDTO loginDto = objectMapper.readValue(request.getInputStream(), MemberLoginDTO.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        Member member = (Member) authResult.getPrincipal();

        String accessToken = refreshTokenService.delegateAccessToken(member);
        String refreshToken = refreshTokenService.delegateRefreshToken(member);

        response.setHeader("Authorization", accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
