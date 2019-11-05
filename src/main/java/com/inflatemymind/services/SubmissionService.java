package com.inflatemymind.services;

import com.inflatemymind.models.Submission;
import com.inflatemymind.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    @Autowired
    SubmissionRepository submissionRepository;

    public List<Submission> getSubmissions() {
        return submissionRepository.findAll();
    }

    public Optional<Submission> getSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId);
    }

    public List<Submission> getSubmissionsByExpressionId(Long expressionId) {
        List<Submission> submissions = this.getSubmissions();
        return submissions.stream()
                .filter(submission -> submission.getExpressionId().equals(expressionId))
                .collect(Collectors.toList());
    }

    public Submission createSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByUserId(Long userId) {
        List<Submission> submissions = this.getSubmissions();
        return submissions.stream()
                .filter(submission -> submission.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
