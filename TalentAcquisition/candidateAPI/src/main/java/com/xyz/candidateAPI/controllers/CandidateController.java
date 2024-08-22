package com.xyz.candidateAPI.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xyz.candidateAPI.entities.Candidate;
import com.xyz.candidateAPI.services.CandidateService;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
	
	 @Autowired
	    private CandidateService candidateService;

	    @PostMapping("/add")
	    public ResponseEntity<Candidate> addCandidate(@RequestParam("name") String name,
	                                                  @RequestParam("rollNumber") String rollNumber,
	                                                  @RequestParam("email") String email,
	                                                  @RequestParam("cgpa") double cgpa,
	                                                  @RequestParam("imageFile") MultipartFile imageFile) {
	        try {
	            Candidate newCandidate = candidateService.addCandidate(name, rollNumber, email, cgpa, imageFile);
	            return ResponseEntity.status(HttpStatus.CREATED).body(newCandidate);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	    
	    @GetMapping("/all")
	    public ResponseEntity<List<Candidate>> getAllCandidates() {
	        List<Candidate> candidates = candidateService.getAllCandidates();
	        return ResponseEntity.ok(candidates);
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
	        Candidate candidate = candidateService.getCandidateById(id);
	        if (candidate == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(candidate);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id,
	                                                     @RequestParam("name") String name,
	                                                     @RequestParam("rollNumber") String rollNumber,
	                                                     @RequestParam("email") String email,
	                                                     @RequestParam("cgpa") double cgpa) {
	        Candidate updatedCandidate = candidateService.updateCandidate(id, name, rollNumber, email, cgpa);
	        if (updatedCandidate == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(updatedCandidate);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
	        candidateService.deleteCandidate(id);
	        return ResponseEntity.noContent().build();
	    }
	    
	    @GetMapping("/search")
		public ResponseEntity<Candidate> searchCandidateByRollNumber(@RequestParam("rollNumber") String rollNumber) {
		    Candidate candidate = candidateService.searchCandidateByRollNumber(rollNumber);
		    if (candidate == null) {
		        return ResponseEntity.notFound().build();
		    }
		    return ResponseEntity.ok(candidate);
		}

}
