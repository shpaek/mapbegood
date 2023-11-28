package com.kosa.mapbegood.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {
    public String getUserEmail(Authentication authentication) {
        return authentication.getPrincipal().toString();
    }
}
