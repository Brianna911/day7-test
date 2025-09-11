package com.example.demo.service;

import com.example.demo.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CompanyService {

    private final List<Company> companies = new ArrayList<>();

    // 创建公司
    public Company createCompany(Company companyDTO) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company, "id");
        company.setId(companies.size() + 1);
        companies.add(company);
        return company;
    }

    // 查询公司
    public Optional<Company> getCompanyById(long id) {
        return companies.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    // 更新公司
    public Optional<Company> updateCompany(int id, Company updatedCompany) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                updatedCompany.setId(id); // 保持原 ID
                companies.set(i, updatedCompany);
                return Optional.of(updatedCompany);
            }
        }
        return Optional.empty();
    }

    // 删除公司
    public boolean deleteCompany(long id) {
        return companies.removeIf(c -> c.getId() == id);
    }
}
