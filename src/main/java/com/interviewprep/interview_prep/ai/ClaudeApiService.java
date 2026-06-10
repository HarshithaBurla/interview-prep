package com.interviewprep.interview_prep.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ClaudeApiService {

    @Value("${groq.api.key}")
    private String apiKey;

    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ================= CORE API CALL =================
    private String callClaude(String systemPrompt, String userMessage) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            Map<String, Object> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", systemPrompt);

            Map<String, Object> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", userMessage);

            Map<String, Object> body = new HashMap<>();
            body.put("model", MODEL);
            body.put("max_tokens", 1024);
            body.put("messages", List.of(systemMsg, userMsg));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    GROQ_URL, entity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("choices").get(0)
                    .path("message").path("content").asText();

        } catch (Exception e) {
            throw new RuntimeException("Groq API call failed: " + e.getMessage());
        }
    }

    // ================= GENERATE A QUESTION =================
    public AiResponse generateQuestion(String topic, String difficulty) {
        String systemPrompt = """
                You are an expert technical interviewer.
                Generate ONE interview question and a model answer.
                Respond in this EXACT format — nothing else:
                
                QUESTION: <the question>
                ANSWER: <a clear, detailed model answer>
                """;

        String userMessage = String.format(
                "Generate a %s difficulty interview question about: %s",
                difficulty != null ? difficulty : "MEDIUM", topic
        );

        String raw = callClaude(systemPrompt, userMessage);

        AiResponse aiResponse = new AiResponse();
        aiResponse.setGeneratedQuestion(extractSection(raw, "QUESTION:"));
        aiResponse.setSuggestedAnswer(extractSection(raw, "ANSWER:"));
        return aiResponse;
    }

    // ================= GRADE AN ANSWER =================
    public AiResponse gradeAnswer(String question, String userAnswer) {
        String systemPrompt = """
                You are a strict but fair technical interviewer grading a candidate's answer.
                Respond in this EXACT format — nothing else:
                
                SCORE: <number from 1 to 10>
                FEEDBACK: <2-3 sentences explaining what was good and what was missing>
                IMPROVED_ANSWER: <a better version of the answer>
                """;

        String userMessage = String.format("""
                Question: %s
                
                Candidate's answer: %s
                
                Grade this answer.
                """, question, userAnswer);

        String raw = callClaude(systemPrompt, userMessage);

        AiResponse aiResponse = new AiResponse();

        String scoreStr = extractSection(raw, "SCORE:").replaceAll("[^0-9]", "").trim();
        try {
            aiResponse.setScore(Integer.parseInt(scoreStr));
        } catch (NumberFormatException e) {
            aiResponse.setScore(5);
        }

        aiResponse.setFeedback(extractSection(raw, "FEEDBACK:"));
        aiResponse.setImprovedAnswer(extractSection(raw, "IMPROVED_ANSWER:"));
        return aiResponse;
    }

    // ================= MOCK INTERVIEW =================
    public AiResponse mockInterview(String topic, String difficulty) {
        String systemPrompt = """
                You are conducting a mock technical interview.
                Generate exactly 5 interview questions with model answers, then summarise.
                Respond in this EXACT format — nothing else:
                
                Q1: <question>
                A1: <model answer>
                
                Q2: <question>
                A2: <model answer>
                
                Q3: <question>
                A3: <model answer>
                
                Q4: <question>
                A4: <model answer>
                
                Q5: <question>
                A5: <model answer>
                
                SUMMARY: <overall advice for a candidate preparing this topic>
                """;

        String userMessage = String.format(
                "Run a mock interview on: %s. Difficulty: %s.",
                topic, difficulty != null ? difficulty : "MEDIUM"
        );

        String raw = callClaude(systemPrompt, userMessage);

        AiResponse aiResponse = new AiResponse();
        aiResponse.setMockInterviewReport(raw);
        return aiResponse;
    }

    // ================= WEAK TOPIC ADVICE (Phase 4) =================
    public String getWeakTopicAdvice(List<String> weakTopics) {
        String systemPrompt = """
                You are a helpful technical interview coach.
                Give concise, actionable advice in 3-4 sentences.
                Be encouraging but specific about what to study.
                """;

        String userMessage = String.format(
                "A candidate is scoring below 6/10 in these topics: %s. " +
                        "What should they focus on to improve? Give specific advice.",
                String.join(", ", weakTopics)
        );

        return callClaude(systemPrompt, userMessage);
    }

    // ================= HELPER =================
    private String extractSection(String text, String label) {
        int start = text.indexOf(label);
        if (start == -1) return text.trim();

        start += label.length();
        String remaining = text.substring(start).trim();
        String[] lines = remaining.split("\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (line.matches("^[A-Z_0-9]+:.*") && result.length() > 0) break;
            result.append(line).append("\n");
        }

        return result.toString().trim();
    }
}