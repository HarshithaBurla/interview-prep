package com.interviewprep.interview_prep.progress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAttemptRepository extends JpaRepository<UserAttempt, Long> {

    // All attempts by a user (newest first)
    List<UserAttempt> findByUserIdOrderByAttemptedAtDesc(Long userId);

    // All attempts by a user for a specific topic
    List<UserAttempt> findByUserIdAndTopicIgnoreCaseOrderByAttemptedAtDesc(Long userId, String topic);

    // Average score per topic for a user
    @Query("SELECT ua.topic, AVG(ua.score) FROM UserAttempt ua " +
            "WHERE ua.user.id = :userId " +
            "GROUP BY ua.topic " +
            "ORDER BY AVG(ua.score) ASC")
    List<Object[]> findAvgScorePerTopic(@Param("userId") Long userId);

    // Total attempts count for a user
    long countByUserId(Long userId);

    // Overall average score for a user
    @Query("SELECT AVG(ua.score) FROM UserAttempt ua WHERE ua.user.id = :userId")
    Double findOverallAvgScore(@Param("userId") Long userId);

    // Weak topics — avg score below a threshold
    @Query("SELECT ua.topic, AVG(ua.score) FROM UserAttempt ua " +
            "WHERE ua.user.id = :userId " +
            "GROUP BY ua.topic " +
            "HAVING AVG(ua.score) < :threshold")
    List<Object[]> findWeakTopics(@Param("userId") Long userId, @Param("threshold") double threshold);
}