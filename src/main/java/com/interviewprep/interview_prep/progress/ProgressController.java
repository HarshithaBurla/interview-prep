package com.interviewprep.interview_prep.progress;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // ===== SUBMIT AN ATTEMPT =====
    // POST /api/progress/attempt
    // Body: { "question": "...", "userAnswer": "...", "topic": "Java", "difficulty": "MEDIUM" }
    // Grades via AI and saves to DB
    @PostMapping("/attempt")
    public ResponseEntity<UserAttempt> submitAttempt(@RequestBody AttemptRequest request) {
        UserAttempt saved = progressService.submitAttempt(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ===== GET FULL PROGRESS DASHBOARD =====
    // GET /api/progress
    // Returns: totalAttempts, overallAvg, scorePerTopic, weakTopics, aiAdvice, recentAttempts
    @GetMapping
    public ResponseEntity<ProgressResponse> getProgress() {
        return ResponseEntity.ok(progressService.getProgress());
    }

    // ===== GET ATTEMPTS BY TOPIC =====
    // GET /api/progress/topic/java
    @GetMapping("/topic/{topic}")
    public ResponseEntity<List<UserAttempt>> getByTopic(@PathVariable String topic) {
        return ResponseEntity.ok(progressService.getAttemptsByTopic(topic));
    }
}