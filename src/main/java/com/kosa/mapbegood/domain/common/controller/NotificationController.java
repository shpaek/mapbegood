package com.kosa.mapbegood.domain.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
//    
//    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public SseEmitter subscribe(Authentication authentication) {
//    	String email = authenticationUtil.getUserEmail(authentication);
//        return notificationService.subscribe(email);
//    }
//a
//    @PostMapping("/send-data")
//    public void sendData(Authentication authentication) {
//    	String email = authenticationUtil.getUserEmail(authentication);
//        notificationService.notify(email, "data");
//    }
    
    //1. 구독신청이며, 로그인을 한후에, 메인화면을 하게 되면 구독신청함
    @GetMapping(value = "/subs/{email}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subs(@PathVariable String email) {
//    	notificationService.get
        return notificationService.subscribe(email);
    }
    //2.그룹초대메세지,보내는 처리,알림메시지가 가는 controller
    @GetMapping("/send/{email}/{data}")
    public void sendData(@PathVariable String email, @PathVariable String data) {
    	System.out.println(email + ":" + data);
    	notificationService.notify(email,data);
    }
   
    
}