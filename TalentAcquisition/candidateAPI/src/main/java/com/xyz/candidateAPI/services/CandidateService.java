package com.xyz.candidateAPI.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xyz.candidateAPI.entities.Candidate;
import com.xyz.candidateAPI.repository.CandidateRepo;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepo candidateRepository;

    // Cloudinary configuration (replace with your Cloudinary details)
    private Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dadpjbuix",
            "api_key", "887486484443372",
            "api_secret", "MgsDLXdE9xs_dEFdJddqJdf7ySE"));

    public Candidate addCandidate(String name, String rollNumber, String email, double cgpa, MultipartFile imageFile) throws IOException {
        // Upload image to Cloudinary and get URL
        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");

        // Create candidate object
        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setRollNumber(rollNumber);
        candidate.setEmail(email);
        candidate.setCgpa(cgpa);
        candidate.setImageUrl(imageUrl);

        // Save candidate to database
        return candidateRepository.save(candidate);
    }
    
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        return optionalCandidate.orElse(null);
    }

    public Candidate updateCandidate(Long id, String name, String rollNumber, String email, double cgpa) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            candidate.setName(name);
            candidate.setRollNumber(rollNumber);
            candidate.setEmail(email);
            candidate.setCgpa(cgpa);
            return candidateRepository.save(candidate);
        }
        return null;
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
    public Candidate searchCandidateByRollNumber(String rollNumber) {
        return candidateRepository.findByRollNumber(rollNumber);
    }
}
