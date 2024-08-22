package com.xyz.interviewAPI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.candidateAPI.entities.Candidate;
import com.xyz.candidateAPI.repository.CandidateRepo;
import com.xyz.interviewAPI.entities.Interview;
import com.xyz.interviewAPI.repository.InterviewRepo;


@Service
public class InterviewService {

    @Autowired
    private InterviewRepo interviewRepository;
    

    @Autowired
    private CandidateRepo candidateRepository; // Inject CandidateRepo


    public Interview scheduleInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    public Interview getInterviewById(Long id) {
        Optional<Interview> optionalInterview = interviewRepository.findById(id);
        return optionalInterview.orElse(null);
    }

    public List<Interview> getInterviewsByCandidateId(Long candidateId) {
        return interviewRepository.findByCandidateId(candidateId);
    }
    
    public Interview updateInterview(Long id, Interview updatedInterview) {
        Interview existingInterview = interviewRepository.findById(id).orElse(null);
        if (existingInterview != null) {
            updatedInterview.setId(existingInterview.getId()); // Ensure the updated interview has the same ID
            return interviewRepository.save(updatedInterview);
        }
        return null; // Interview not found
    }
    
    public Interview updateInterviewResult(Long id, String result) {
        Interview interview = interviewRepository.findById(id).orElse(null);
        if (interview != null) {
            interview.setInterviewResult(result);
            return interviewRepository.save(interview);
        }
        return null;
    }

    public void deleteInterview(Long id) {
        interviewRepository.deleteById(id);
    }
    
   

    public Interview linkResumeWithInterview(Long id, String resumeUrl) {
        Interview interview = interviewRepository.findById(id).orElse(null);
        if (interview != null) {
            Candidate candidate = interview.getCandidate();
            if (candidate != null) {
                candidate.setImageUrl(resumeUrl);
                candidateRepository.save(candidate); // Update candidate with resume URL
                return interview;
            }
        }
        return null;
    }
   
    public String searchInterviewResultByCandidateRollNumber(String rollNumber) {
        List<Interview> interviews = interviewRepository.findByCandidateRollNumber(rollNumber);
        if (interviews != null && !interviews.isEmpty()) {
            return interviews.get(0).getInterviewResult();
        }
        return null;
    }
}
