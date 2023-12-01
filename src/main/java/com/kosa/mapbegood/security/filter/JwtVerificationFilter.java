package com.kosa.mapbegood.security.filter;

import com.kosa.mapbegood.security.jwt.JwtTokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = jwtUtil.resolveToken(request);
//        if (token != null) {
//            if (!jwtUtil.validateToken(token)) { //쿠키가 만료되면 false가되어 밑에 코드가 실행됌
//                Cookie rc[] = request.getCookies(); //request에서 쿠키를 받아온다.
//                String refreshToken = "";
//                for (Cookie cookie : rc) { //벨류에 넣었던 리프레쉬토큰을 꺼내서 넣어준다.
//                    refreshToken = cookie.getValue();
//                }
//                if (request.getCookies() != null && jwtUtil.getRefreshTokenEquals(refreshToken)) { //디비의 리프레시 토큰의 유효성 검사를 해준다.
//                    Claims info = jwtUtil.getUserInfoFromToken(refreshToken); //리프레쉬토큰의 정보를 꺼내온다.
//                    setAuthentication(info.getSubject()); //리프레쉬토큰의 정보를 인증객체로 바꾸어준다.
//                    User authUser = jwtUtil.Authenticateduser(info.getSubject());
//                    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createAccessToken(info.getSubject(), authUser.getRole())); //다시 어세스토큰을 발급해준다.
//                }
//            } else {
//                Claims info = jwtUtil.getUserInfoFromToken(token);
//                setAuthentication(info.getSubject());
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer ");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
