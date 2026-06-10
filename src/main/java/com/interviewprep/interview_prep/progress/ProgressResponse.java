package com.interviewprep.interview_prep.progress;

import java.util.List;
import java.util.Map;

public class ProgressResponse {

    private long totalAttempts;
    private double overallAverageScore;

    // topic → average score
    private Map<String, Double> scorePerTopic;

    // topics where avg score < 6
    private List<String> weakTopics;

    // AI-generated advice based on weak topics
    private String aiAdvice;

    // last 10 attempts
    private List<AttemptSummary> recentAttempts;

    // ===== Getters & Setters =====

    public long getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(long totalAttempts) { this.totalAttempts = totalAttempts; }

    public double getOverallAverageScore() { return overallAverageScore; }
    public void setOverallAverageScore(double overallAverageScore) { this.overallAverageScore = overallAverageScore; }

    public Map<String, Double> getScorePerTopic() { return scorePerTopic; }
    public void setScorePerTopic(Map<String, Double> scorePerTopic) { this.scorePerTopic = scorePerTopic; }

    public List<String> getWeakTopics() { return weakTopics; }
    public void setWeakTopics(List<String> weakTopics) { this.weakTopics = weakTopics; }

    public String getAiAdvice() { return aiAdvice; }
    public void setAiAdvice(String aiAdvice) { this.aiAdvice = aiAdvice; }

    public List<AttemptSummary> getRecentAttempts() { return recentAttempts; }
    public void setRecentAttempts(List<AttemptSummary> recentAttempts) { this.recentAttempts = recentAttempts; }

    // ===== Inner class for recent attempt summary =====
    public static class AttemptSummary {
        private Long id;
        private String question;
        private Integer score;
        private String topic;
        private String difficulty;
        private String attemptedAt;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }

        public Integer getScore() { return score; }
        public void setScore(Integer score) { this.score = score; }

        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }

        public String getDifficulty() { return difficulty; }
        public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

        public String getAttemptedAt() { return attemptedAt; }
        public void setAttemptedAt(String attemptedAt) { this.attemptedAt = attemptedAt; }
    }
}