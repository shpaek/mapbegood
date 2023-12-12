package com.kosa.mapbegood.security.refresh;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.exception.FindException;
import com.kosa.mapbegood.security.jwt.JwtTokenizer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository repository;
    private final MemberRepository memberRepository;

    public String delegateAccessToken(Member member) {
        String email = member.getEmail();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("roles", "ROLE_USER");

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.generateAccessToken(claims, email, expiration, base64EncodedSecretKey);
    }

    public String delegateRefreshToken(Member member) {
        String subject = member.getEmail();

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }

    public String refreshAccessToken(String refreshTokenBearer) throws Exception {
        String refreshToken = refreshTokenBearer.substring(7);
        Optional<RefreshToken> optRefreshToken = repository.findById(refreshToken);

        if (optRefreshToken.isEmpty()) {
            Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));
            String email = claims.getPayload().getSubject();
            Optional<Member> optMember = memberRepository.findById(email);

            if (optMember.isPresent()) {
                return delegateAccessToken(optMember.get());
            } else {
                throw new FindException("인증 토큰의 회원을 조회할 수 없습니다.");
            }
        } else {
            throw new FindException("로그아웃된 토큰 입니다.");
        }
    }

    public void logout(String refreshTokenBearer) throws Exception {
        String refreshToken = refreshTokenBearer.substring(7);
        RefreshToken refreshTokenObj = new RefreshToken(refreshToken, "logout");
        repository.save(refreshTokenObj);
    }
}
