package com.interviewprep.interview_prep.question;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // ================= GET ALL (with optional filters) =================
    // Any logged-in user can access
    // Examples:
    //   GET /api/questions
    //   GET /api/questions?topic=java
    //   GET /api/questions?topic=java&difficulty=easy
    //   GET /api/questions?topic=spring&difficulty=medium&type=conceptual
    @GetMapping
    public ResponseEntity<List<Question>> getAll(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String type) {

        List<Question> questions = questionService.getQuestions(topic, difficulty, type);
        return ResponseEntity.ok(questions);
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    // ================= CREATE (Admin only) =================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question> create(@RequestBody QuestionRequest request) {
        Question created = questionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ================= UPDATE (Admin only) =================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question> update(@PathVariable Long id,
                                           @RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.update(id, request));
    }

    // ================= DELETE (Admin only) =================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Question deleted successfully"));
    }
}