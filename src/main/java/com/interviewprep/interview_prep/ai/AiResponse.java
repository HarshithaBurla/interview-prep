package com.interviewprep.interview_prep.ai;

public class AiResponse {

    private String generatedQuestion;   // used by /generate
    private String suggestedAnswer;     // used by /generate

    private Integer score;              // used by /grade (1-10)
    private String feedback;            // used by /grade
    private String improvedAnswer;      // used by /grade

    private String mockInterviewReport; // used by /mock-interview

    // ===== Getters & Setters =====

    public String getGeneratedQuestion() { return generatedQuestion; }
    public void setGeneratedQuestion(String generatedQuestion) { this.generatedQuestion = generatedQuestion; }

    public String getSuggestedAnswer() { return suggestedAnswer; }
    public void setSuggestedAnswer(String suggestedAnswer) { this.suggestedAnswer = suggestedAnswer; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public String getImprovedAnswer() { return improvedAnswer; }
    public void setImprovedAnswer(String improvedAnswer) { this.improvedAnswer = improvedAnswer; }

    public String getMockInterviewReport() { return mockInterviewReport; }
    public void setMockInterviewReport(String mockInterviewReport) { this.mockInterviewReport = mockInterviewReport; }
}