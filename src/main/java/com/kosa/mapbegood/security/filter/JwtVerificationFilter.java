package com.kosa.mapbegood.security.filter;

import com.kosa.mapbegood.security.jwt.JwtTokenizer;
import com.kosa.mapbegood.security.response.ErrorResponder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = this.verifyJws(request);
            this.setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            log.error("토큰 인증 Error: " + se.getMessage());
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "The token information is incorrect.");
        } catch (ExpiredJwtException ee) {
            log.error("토큰 만료 Error: " + ee.getMessage());
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "The token has expired.");
        } catch (Exception e) {
            log.error("토큰 Error: " + e.getMessage());
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null;
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) throws Exception {
        if(!request.getHeader("Authorization").startsWith("Bearer ")) {
            throw new SignatureException("");
        }
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        log.error("jws: " + jws);
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        return jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getPayload();
    }

    private void setAuthenticationToContext(Map<String, Object> claims) throws Exception {
        try {
            String username = (String) claims.get("email");
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureException se) {
            throw new SignatureException("");
        }
    }
}
