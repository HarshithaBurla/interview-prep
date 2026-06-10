package com.interviewprep.interview_prep.progress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.interviewprep.interview_prep.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_attempts")
public class UserAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @JsonIgnore prevents Jackson from trying to serialize the User proxy
    // which causes "no session" error with lazy loading
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(nullable = false, length = 3000)
    private String userAnswer;

    @Column(length = 2000)
    private String feedback;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private String topic;

    private String difficulty;

    @Column(nullable = false)
    private LocalDateTime attemptedAt;

    @PrePersist
    public void prePersist() {
        this.attemptedAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public LocalDateTime getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }
}