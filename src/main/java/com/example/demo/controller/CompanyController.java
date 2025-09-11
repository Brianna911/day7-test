package com.example.demo.controller;

import com.example.demo.Company;
import com.example.demo.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Controller
@RequestMapping("/companys")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCompany(@RequestBody Company companyDTO) {
        Company company = companyService.createCompany(companyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", company.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(id, updatedCompany)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        boolean removed = companyService.deleteCompany(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
