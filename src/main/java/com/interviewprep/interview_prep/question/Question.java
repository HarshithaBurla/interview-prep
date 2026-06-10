package com.interviewprep.interview_prep.question;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Column(nullable = false, length = 2000)
    private String answer;

    // e.g. "Java", "Spring Boot", "SQL", "DSA"
    @Column(nullable = false)
    private String topic;

    // e.g. "EASY", "MEDIUM", "HARD"
    @Column(nullable = false)
    private String difficulty;

    // e.g. "CONCEPTUAL", "CODING", "SCENARIO"
    @Column(nullable = false)
    private String type;

    // ===== Getters & Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}