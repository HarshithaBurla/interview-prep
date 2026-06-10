package com.interviewprep.interview_prep.ai;

public class AiRequest {

    private String topic;       // e.g. "Java", "Spring Boot", "DSA"
    private String difficulty;  // e.g. "EASY", "MEDIUM", "HARD"
    private String question;    // the question that was asked (for grading)
    private String userAnswer;  // the user's answer (for grading)

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getUserAnswer() { return userAnswer; }
    public void setUserAnswer(String userAnswer) { this.userAnswer = userAnswer; }
}