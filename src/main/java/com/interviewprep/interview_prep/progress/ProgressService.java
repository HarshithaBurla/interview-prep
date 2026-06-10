package com.interviewprep.interview_prep.progress;

import com.interviewprep.interview_prep.ai.AiResponse;
import com.interviewprep.interview_prep.ai.ClaudeApiService;
import com.interviewprep.interview_prep.user.User;
import com.interviewprep.interview_prep.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final UserAttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final ClaudeApiService claudeApiService;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ProgressService(UserAttemptRepository attemptRepository,
                           UserRepository userRepository,
                           ClaudeApiService claudeApiService) {
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.claudeApiService = claudeApiService;
    }

    // ===== Get currently logged-in user =====
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));
    }

    // ===== Submit an attempt — grades it with AI and saves =====
    public UserAttempt submitAttempt(AttemptRequest request) {
        User user = getCurrentUser();

        // Grade with AI
        AiResponse grading = claudeApiService.gradeAnswer(
                request.getQuestion(), request.getUserAnswer());

        // Save attempt
        UserAttempt attempt = new UserAttempt();
        attempt.setUser(user);
        attempt.setQuestion(request.getQuestion());
        attempt.setUserAnswer(request.getUserAnswer());
        attempt.setScore(grading.getScore() != null ? grading.getScore() : 5);
        attempt.setFeedback(grading.getFeedback());
        attempt.setTopic(request.getTopic() != null
                ? request.getTopic().toUpperCase() : "GENERAL");
        attempt.setDifficulty(request.getDifficulty() != null
                ? request.getDifficulty().toUpperCase() : "MEDIUM");

        return attemptRepository.save(attempt);
    }

    // ===== Get full progress dashboard =====
    public ProgressResponse getProgress() {
        User user = getCurrentUser();
        Long userId = user.getId();

        // Total attempts
        long total = attemptRepository.countByUserId(userId);

        // Overall average score
        Double avg = attemptRepository.findOverallAvgScore(userId);
        double overallAvg = avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;

        // Score per topic
        List<Object[]> topicScores = attemptRepository.findAvgScorePerTopic(userId);
        Map<String, Double> scorePerTopic = new LinkedHashMap<>();
        for (Object[] row : topicScores) {
            String topic = (String) row[0];
            Double score = Math.round(((Double) row[1]) * 10.0) / 10.0;
            scorePerTopic.put(topic, score);
        }

        // Weak topics (avg < 6)
        List<Object[]> weakRows = attemptRepository.findWeakTopics(userId, 6.0);
        List<String> weakTopics = weakRows.stream()
                .map(row -> (String) row[0])
                .collect(Collectors.toList());

        // AI advice for weak topics
        String aiAdvice = "";
        if (!weakTopics.isEmpty()) {
            aiAdvice = claudeApiService.getWeakTopicAdvice(weakTopics);
        } else if (total > 0) {
            aiAdvice = "Great work! Keep practicing to maintain your scores.";
        } else {
            aiAdvice = "No attempts yet. Start practicing to see your progress!";
        }

        // Recent 10 attempts
        List<UserAttempt> recent = attemptRepository
                .findByUserIdOrderByAttemptedAtDesc(userId)
                .stream().limit(10).collect(Collectors.toList());

        List<ProgressResponse.AttemptSummary> summaries = recent.stream().map(a -> {
            ProgressResponse.AttemptSummary s = new ProgressResponse.AttemptSummary();
            s.setId(a.getId());
            s.setQuestion(a.getQuestion().length() > 80
                    ? a.getQuestion().substring(0, 80) + "..." : a.getQuestion());
            s.setScore(a.getScore());
            s.setTopic(a.getTopic());
            s.setDifficulty(a.getDifficulty());
            s.setAttemptedAt(a.getAttemptedAt().format(FORMATTER));
            return s;
        }).collect(Collectors.toList());

        // Build response
        ProgressResponse response = new ProgressResponse();
        response.setTotalAttempts(total);
        response.setOverallAverageScore(overallAvg);
        response.setScorePerTopic(scorePerTopic);
        response.setWeakTopics(weakTopics);
        response.setAiAdvice(aiAdvice);
        response.setRecentAttempts(summaries);
        return response;
    }

    // ===== Get attempts for a specific topic =====
    public List<UserAttempt> getAttemptsByTopic(String topic) {
        User user = getCurrentUser();
        return attemptRepository
                .findByUserIdAndTopicIgnoreCaseOrderByAttemptedAtDesc(
                        user.getId(), topic);
    }
}