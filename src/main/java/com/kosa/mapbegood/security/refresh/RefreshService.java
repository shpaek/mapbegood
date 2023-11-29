package com.kosa.mapbegood.security.refresh;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenizer jwtTokenizer;

    // Access Token을 생성하는 구체적인 로직
    public String delegateAccessToken(Member member) {
        String email = member.getEmail();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", "ROLE_USER");

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, email, expiration, base64EncodedSecretKey);

        return "Bearer " + accessToken;
    }

    // Refresh Token을 생성하는 구체적인 로직
    public String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

//        RefreshToken rtk = new RefreshToken(refreshToken, "LogOut");
//        refreshTokenRepository.save(rtk);

        return "Bearer " + refreshToken;
    }
}
