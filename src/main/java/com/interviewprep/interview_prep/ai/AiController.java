package com.interviewprep.interview_prep.ai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final ClaudeApiService claudeApiService;

    public AiController(ClaudeApiService claudeApiService) {
        this.claudeApiService = claudeApiService;
    }

    // ================= GENERATE A QUESTION =================
    // POST /api/ai/generate
    // Body: { "topic": "Java", "difficulty": "MEDIUM" }
    // Returns: { "generatedQuestion": "...", "suggestedAnswer": "..." }
    @PostMapping("/generate")
    public ResponseEntity<AiResponse> generate(@RequestBody AiRequest request) {
        AiResponse response = claudeApiService.generateQuestion(
                request.getTopic(),
                request.getDifficulty()
        );
        return ResponseEntity.ok(response);
    }

    // ================= GRADE AN ANSWER =================
    // POST /api/ai/grade
    // Body: { "question": "What is JVM?", "userAnswer": "JVM runs Java code..." }
    // Returns: { "score": 7, "feedback": "...", "improvedAnswer": "..." }
    @PostMapping("/grade")
    public ResponseEntity<AiResponse> grade(@RequestBody AiRequest request) {
        AiResponse response = claudeApiService.gradeAnswer(
                request.getQuestion(),
                request.getUserAnswer()
        );
        return ResponseEntity.ok(response);
    }

    // ================= MOCK INTERVIEW =================
    // POST /api/ai/mock-interview
    // Body: { "topic": "Spring Boot", "difficulty": "HARD" }
    // Returns: { "mockInterviewReport": "Q1: ... A1: ... Q2: ..." }
    @PostMapping("/mock-interview")
    public ResponseEntity<AiResponse> mockInterview(@RequestBody AiRequest request) {
        AiResponse response = claudeApiService.mockInterview(
                request.getTopic(),
                request.getDifficulty()
        );
        return ResponseEntity.ok(response);
    }
}