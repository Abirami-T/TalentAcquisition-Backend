package com.xyz.candidateAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.candidateAPI.entities.Candidate;

public interface CandidateRepo extends JpaRepository<Candidate, Long>{

	 Candidate findByRollNumber(String rollNumber);
	 
}
