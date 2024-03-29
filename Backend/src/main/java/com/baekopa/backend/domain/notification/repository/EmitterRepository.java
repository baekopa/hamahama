package com.baekopa.backend.domain.notification.repository;

import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    void save(String emitterId, SseEmitter emitter);

    void save(String eventId, NotificationResponseDto event);

    Map<String, SseEmitter> findAllEmitterStartWithKey(String key);

    Map<String, NotificationResponseDto> findAllEventStartWithKey(String key);

    void deleteById(String id);

    void deleteAllEmitterStartWithKey(String key);

    void deleteAllEventStartWithKey(String key);
}
