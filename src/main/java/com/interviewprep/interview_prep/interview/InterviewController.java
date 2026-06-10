package com.interviewprep.interview_prep.interview;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterviewController {

    @GetMapping("/api/interview/questions")
    public String questions() {
        return "Top Java Interview Questions";
    }
}