package com.baekopa.backend.domain.notification.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverAndIsCheckedIsFalseAndDeletedAtIsNullOrderByCreatedAtDesc(Member receiver);

    Optional<Notification> findByIdAndReceiver(Long id, Member receiver);

    List<Notification> findAllByReceiverAndDeletedAtIsNullOrderByCreatedAtDesc(Member member);
}
