package com.kosa.mapbegood.domain.common.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
@RequiredArgsConstructor
public class EmitterRepository {
    // 모든 Emitters를 저장하는 ConcurrentHashMap
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * 주어진 아이디와 이미터를 저장
     * @param userEmail - 사용자 이메일.
     * @param emitter - 이벤트 Emitter.
     */
    public void save(String userEmail, SseEmitter emitter) {
    	log.error("EmitterRepository save");
        emitters.put(userEmail, emitter);
    }

    /**
     * 주어진 아이디의 Emitter를 제거
     * @param userEmail - 사용자 이메일.
     */
    
    public void deleteById(String userEmail) {
        emitters.remove(userEmail);
    }

    /**
     * 주어진 아이디의 Emitter를 가져옴.
     * @param userEmail - 사용자 이메일.
     * @return SseEmitter - 이벤트 Emitter.
     */
    
    public SseEmitter get(String userEmail) {
        return emitters.get(userEmail);
    }
}