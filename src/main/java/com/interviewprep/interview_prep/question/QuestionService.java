package com.interviewprep.interview_prep.question;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // Get all questions (optional filters)
    public List<Question> getQuestions(String topic, String difficulty, String type) {
        return questionRepository.findByFilters(topic, difficulty, type);
    }

    // Get single question by ID
    public Question getById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found with id: " + id));
    }

    // Create a new question (Admin only)
    public Question create(QuestionRequest request) {
        validateRequest(request);

        Question question = new Question();
        question.setQuestionText(request.getQuestionText());
        question.setAnswer(request.getAnswer());
        question.setTopic(request.getTopic().toUpperCase());
        question.setDifficulty(request.getDifficulty().toUpperCase());
        question.setType(request.getType().toUpperCase());

        return questionRepository.save(question);
    }

    // Update existing question (Admin only)
    public Question update(Long id, QuestionRequest request) {
        Question existing = getById(id);
        validateRequest(request);

        existing.setQuestionText(request.getQuestionText());
        existing.setAnswer(request.getAnswer());
        existing.setTopic(request.getTopic().toUpperCase());
        existing.setDifficulty(request.getDifficulty().toUpperCase());
        existing.setType(request.getType().toUpperCase());

        return questionRepository.save(existing);
    }

    // Delete question (Admin only)
    public void delete(Long id) {
        Question existing = getById(id);
        questionRepository.delete(existing);
    }

    // Validate required fields
    private void validateRequest(QuestionRequest request) {
        if (request.getQuestionText() == null || request.getQuestionText().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question text is required");
        }
        if (request.getAnswer() == null || request.getAnswer().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Answer is required");
        }
        if (request.getTopic() == null || request.getTopic().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic is required");
        }
        if (request.getDifficulty() == null || request.getDifficulty().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Difficulty is required");
        }
        if (request.getType() == null || request.getType().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type is required");
        }
    }
}