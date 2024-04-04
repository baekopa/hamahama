package com.baekopa.backend.domain.notification.repository;

import com.baekopa.backend.domain.member.entity.Member;
import com.baekopa.backend.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverAndIsCheckedIsFalseAndDeletedAtIsNullOrderByCreatedAtDesc(Member receiver);

    Optional<Notification> findByIdAndReceiverAndDeletedAtIsNull(Long id, Member receiver);

    List<Notification> findAllByReceiverAndDeletedAtIsNullOrderByCreatedAtDesc(Member member);

    @Modifying
    @Query(value = "DELETE FROM notification WHERE MONTH(deleted_at) = :thresholdDate", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);
}
