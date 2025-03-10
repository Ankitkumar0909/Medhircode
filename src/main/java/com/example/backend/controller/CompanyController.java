package com.example.backend.controller;

import com.example.backend.model.CompanyModel;
import com.example.backend.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/superadmin/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createCompany(@Valid @RequestBody CompanyModel company) {
        CompanyModel savedCompany = companyService.createCompany(company);
        return ResponseEntity.ok(Map.of("message", "Company created successfully!", "id", savedCompany.getId()));
    }

    @GetMapping
    public List<CompanyModel> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateCompany(@PathVariable String id, @Valid @RequestBody CompanyModel company) {
        companyService.updateCompany(id, company);
        return ResponseEntity.ok(Map.of("message", "Company updated successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(Map.of("message", "Company deleted successfully!"));
    }
}
