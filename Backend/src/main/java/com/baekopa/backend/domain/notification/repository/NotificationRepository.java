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

    Optional<Notification> findByIdAndReceiverAndDeletedAtIsNull(Long id, Member receiver);

    List<Notification> findByReceiverAndDeletedAtIsNullOrderByCreatedAtDesc(Member member);

    List<Notification> findByReceiverAndIsCheckedIsFalseAndDeletedAtIsNullOrderByCreatedAtDesc(Member receiver);

    @Modifying
    @Query(value = "DELETE FROM notification WHERE MONTH(deleted_at)  <= MONTH(:thresholdDate)", nativeQuery = true)
    int deleteSoftDeletedBeforeDate(Timestamp thresholdDate);

    @Modifying
    @Query(value = "DELETE FROM notification WHERE member_id = :memberId", nativeQuery = true)
    int deleteByReceiver(Long memberId);
}
