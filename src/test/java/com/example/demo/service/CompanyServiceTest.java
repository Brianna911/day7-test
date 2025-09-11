package com.example.demo.service;

import com.example.demo.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyServiceTest {

    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        companyService = new CompanyService();
    }

    @Test
    public void testCreateCompany() {
        Company company = new Company();
        company.setName("Test Company");
        company.setId(1);

        Company created = companyService.createCompany(company);

        assertThat(created.getId()).isEqualTo(1);
        assertThat(created.getName()).isEqualTo("Test Company");
        assertThat(created.getId()).isEqualTo(1);
    }

    @Test
    public void testGetCompanyById() {
        Company company = new Company();
        company.setName("Company A");
        company.setId(1);
        companyService.createCompany(company);

        Optional<Company> result = companyService.getCompanyById(1);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Company A");
    }

    @Test
    public void testUpdateCompany() {
        Company company = new Company();
        company.setName("Original");
        company.setId(1);
        companyService.createCompany(company);

        Company updated = new Company();
        updated.setName("Updated");
        updated.setId(1);

        Optional<Company> result = companyService.updateCompany(1, updated);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Updated");
        assertThat(result.get().getId()).isEqualTo(1);
    }

    @Test
    public void testDeleteCompany() {
        Company company = new Company();
        company.setName("To Delete");
        company.setId(1);
        companyService.createCompany(company);

        boolean deleted = companyService.deleteCompany(1);

        assertThat(deleted).isTrue();
        assertThat(companyService.getCompanyById(1)).isEmpty();
    }

    @Test
    public void testGetCompanyByInvalidId() {
        Optional<Company> result = companyService.getCompanyById(999);
        assertThat(result).isEmpty();
    }
}
