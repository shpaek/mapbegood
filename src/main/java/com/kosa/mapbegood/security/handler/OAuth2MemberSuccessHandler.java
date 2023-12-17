package com.kosa.mapbegood.security.handler;

import com.kosa.mapbegood.domain.member.entity.Member;
import com.kosa.mapbegood.domain.member.repository.MemberRepository;
import com.kosa.mapbegood.exception.AddException;
import com.kosa.mapbegood.security.refresh.TokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private MemberRepository repository;
    private TokenService tokenService;

    public OAuth2MemberSuccessHandler(MemberRepository repository,
                                      TokenService tokenService
                                      ) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String providerType = authToken.getAuthorizedClientRegistrationId();

        Member member;
        String email;
        String nickName;
        String profileImage;

        if ("kakao".equals(providerType)) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

            email = (String) kakaoAccount.get("email");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            nickName = (String) profile.get("nickname");
            profileImage = (String) profile.get("profile_image_url");
        } else {
            log.error("Kakao OAuth2 Authentication Error");
            throw new AddException("카카오 OAuth 토큰 Error");
        }

        Optional<Member> optMember = repository.findById(email);

        if (optMember.isEmpty()) {
            member = this.saveMember(email, nickName, profileImage);
        } else {
            member = optMember.get();
        }
        this.redirect(request, response, member);
    }

    private Member saveMember(String email, String nickname, String profileImage) {
        Member member = Member.builder()
                .email(email)
                .password(RandomStringUtils.randomAlphanumeric(50))
                .nickname(nickname)
                .profileImage(profileImage)
                .status(2L)
                .build();

        return repository.save(member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, Member member) throws IOException {
        String accessToken = tokenService.delegateAccessToken(member);
        String refreshToken = tokenService.delegateRefreshToken(member);

        response.setHeader("Authorization", accessToken);
        response.setHeader("Refresh", refreshToken);

        String uri = this.createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);

        log.info("# Authenticated successfully!");

        // response 헤더 정보 로그 출력
        for (String headerName : response.getHeaderNames()) {
            log.info(headerName + ": " + response.getHeader(headerName));
        }
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port("5173")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
