package com.xyz.interviewAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.candidateAPI.entities.Candidate;
import com.xyz.interviewAPI.entities.Interview;

public interface InterviewRepo extends JpaRepository<Interview, Long>{
	
	
	List<Interview> findByCandidateId(Long candidateId); // Add method to find interviews by resume URL
	List<Interview> findByCandidateRollNumber(String rollNumber);

	 List<Interview> findByCandidate(Candidate candidate);
}
