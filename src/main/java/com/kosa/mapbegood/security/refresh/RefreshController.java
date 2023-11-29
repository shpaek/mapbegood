package com.kosa.mapbegood.security.refresh;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.security.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RefreshController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberRepository memberRepository;
    private final RefreshService refreshService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("refreshToken");
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String refreshToken = refreshTokenHeader.substring(7);
            try {
                Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findById(refreshToken);
                if (!optRefreshToken.isPresent()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token [redis]");
                }

                Jws<Claims> claims = jwtTokenizer.getClaims(refreshToken, jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()));

                String email = claims.getBody().getSubject();
                Optional<Member> optionalMember = memberRepository.findById(email);

                if (optionalMember.isPresent()) {
                    Member member = optionalMember.get();
//                    String accessToken = tokenService.delegateAccessToken(member);
//                    return ResponseEntity.ok().header("Authorization", "Bearer " + accessToken).body("Access token refreshed");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid member email");
                }
            } catch (JwtException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh token");
        }
        return null;
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("Refresh");
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String refreshToken = refreshTokenHeader.substring(7);
            try {
                Optional<RefreshToken> refreshTokenObj = refreshTokenRepository.findById(refreshToken);
                if (!refreshTokenObj.isPresent()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token [redis]");
                }

                refreshTokenRepository.deleteById(refreshToken);
                return ResponseEntity.ok().body("Logged out successfully, refresh token deleted");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting refresh token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing refresh token");
        }
    }
}
