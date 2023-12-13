package com.kosa.mapbegood.domain.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kosa.mapbegood.domain.common.service.NotificationService;
import com.kosa.mapbegood.util.AuthenticationUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @Autowired
	private AuthenticationUtil authenticationUtil;
    
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(Authentication authentication) {
    	String email = authenticationUtil.getUserEmail(authentication);
        return notificationService.subscribe(email);
    }

    @PostMapping("/send-data")
    public void sendData(Authentication authentication) {
    	String email = authenticationUtil.getUserEmail(authentication);
        notificationService.notify(email, "data");
    }
}