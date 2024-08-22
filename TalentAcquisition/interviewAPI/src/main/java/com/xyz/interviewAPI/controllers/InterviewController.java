package com.xyz.interviewAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.interviewAPI.entities.Interview;
import com.xyz.interviewAPI.services.InterviewService;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping("/schedule")
    public ResponseEntity<Interview> scheduleInterview(@RequestBody Interview interview) {
        Interview scheduledInterview = interviewService.scheduleInterview(interview);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduledInterview);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Interview>> getAllInterviews() {
        List<Interview> interviews = interviewService.getAllInterviews();
        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Long id) {
        Interview interview = interviewService.getInterviewById(id);
        if (interview == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(interview);
    }

    @PutMapping("/{id}/update-result")
    public ResponseEntity<Interview> updateInterviewResult(@PathVariable Long id, @RequestParam String result) {
        Interview updatedInterview = interviewService.updateInterviewResult(id, result);
        if (updatedInterview == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedInterview);
    }

    @PutMapping("/{id}/link-resume")
    public ResponseEntity<Interview> linkResumeWithInterview(@PathVariable Long id, @RequestParam String resumeUrl) {
        Interview linkedInterview = interviewService.linkResumeWithInterview(id, resumeUrl);
        if (linkedInterview == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(linkedInterview);
    }
   
    @GetMapping("/search")
    public ResponseEntity<String> searchInterviewResultByCandidateRollNumber(@RequestParam("rollNumber") String rollNumber) {
        String interviewResult = interviewService.searchInterviewResultByCandidateRollNumber(rollNumber);
        if (interviewResult == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(interviewResult);
    }
}