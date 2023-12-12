package com.kosa.mapbegood.security.refresh;

import com.kosa.mapbegood.exception.FindException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final TokenService service;

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) {
        try {
            String refreshTokenHeader = request.getHeader("Refresh");
            if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
                String accessToken = service.refreshAccessToken(refreshTokenHeader);
                return ResponseEntity.ok().header("Authorization", accessToken).body("AccessToken Refreshed");
            } else {
                throw new FindException();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Token");
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            String refreshTokenBearer = request.getHeader("Refresh");
            if (refreshTokenBearer != null && refreshTokenBearer.startsWith("Bearer ")) {
                service.logout(refreshTokenBearer);
            } else {
                throw new FindException("Invalid RefreshToken");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Logout");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Logout Succeed");
    }
}
