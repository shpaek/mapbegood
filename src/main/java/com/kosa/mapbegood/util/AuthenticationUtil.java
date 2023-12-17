package com.kosa.mapbegood.util;

import com.kosa.mapbegood.exception.FindException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {
    public static String getUserEmail(Authentication authentication) throws Exception {
        return authentication.getPrincipal().toString();
    }
}
