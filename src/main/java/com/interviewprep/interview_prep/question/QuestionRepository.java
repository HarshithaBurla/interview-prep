package com.interviewprep.interview_prep.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // CAST(:param AS string) tells Hibernate to treat null params as text,
    // not bytea — fixes "function lower(bytea) does not exist" on PostgreSQL
    @Query("SELECT q FROM Question q WHERE " +
            "(:topic IS NULL OR LOWER(q.topic) = LOWER(CAST(:topic AS string))) AND " +
            "(:difficulty IS NULL OR LOWER(q.difficulty) = LOWER(CAST(:difficulty AS string))) AND " +
            "(:type IS NULL OR LOWER(q.type) = LOWER(CAST(:type AS string)))")
    List<Question> findByFilters(
            @Param("topic") String topic,
            @Param("difficulty") String difficulty,
            @Param("type") String type);
}