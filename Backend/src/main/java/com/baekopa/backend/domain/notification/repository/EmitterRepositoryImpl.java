package com.baekopa.backend.domain.notification.repository;

import com.baekopa.backend.domain.notification.dto.response.NotificationResponseDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, NotificationResponseDto> events = new ConcurrentHashMap<>();

    @Override
    public void save(String emitterId, SseEmitter emitter) {

        emitters.put(emitterId, emitter);
    }

    @Override
    public void save(String eventId, NotificationResponseDto responseDto) {

        events.put(eventId, responseDto);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithKey(String key) {

        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(key))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, NotificationResponseDto> findAllEventStartWithKey(String key) {

        return events.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(key))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String emitterId) {

        emitters.remove(emitterId);
    }

    @Override
    public void deleteAllEmitterStartWithKey(String key) {

        emitters.forEach(
                (emitterId, emitter) -> {
                    if (emitterId.startsWith(key)) {
                        emitters.remove(emitterId);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventStartWithKey(String key) {

        events.forEach(
                (eventId, emitter) -> {
                    if (eventId.startsWith(key)) {
                        emitters.remove(eventId);
                    }
                }
        );
    }
}
