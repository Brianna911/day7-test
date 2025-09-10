package com.example.demo.controller;
import com.example.demo.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Controller
@RequestMapping("/companys")
public class CompanyController {

    private List<Company> companys = new ArrayList<>();

    // 创建公司（方式1）
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCompany(@RequestBody Company companyDTO) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company, "id");
        company.setId(companys.size() + 1);
        companys.add(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", company.getId()));
    }
//
//    // 创建公司（方式2）返回所有员工列表
//    @PostMapping("/2")
//    public ResponseEntity<Map<String, Object>> createCompany1(@RequestBody Company company) {
//        company.setId(companys.size() + 1);
//        companys.add(company);
//        Map<String, Object> result = new HashMap<>();
//        result.put("companys", companys);
//        return ResponseEntity.ok(result);
//    }

    // 根据 ID 查询公司
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable long id) {
        return companys.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Company> updateCompany(@PathVariable long id, @RequestBody Company updatedCompany) {
//        boolean update = companys.removeIf(e -> e.getId() == id);
//        if (update) {
//            Company existingCompany = new Company(updatedCompany.getName(),updatedCompany.getId());
//
//            // 更新字段
//            existingCompany.setName(updatedCompany.getName());
//            existingCompany.setId(updatedCompany.getId());
//            // 根据你的 Company 类添加其他字段更新
//
//            Company savedCompany = companyRepository.save(existingCompany);
//            return ResponseEntity.ok(savedCompany);
//        } else {
//            return ResponseEntity.notFound().build();
//        } // 没找到该公司，返回 404
//    }
//
//

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable long id, @RequestBody Company updatedCompany) {
        for (int i = 0; i < companys.size(); i++) {
            if (companys.get(i).getId() == id) {
                updatedCompany.setId(updatedCompany.getId()); // 保持原 ID 不变
                companys.set(i, updatedCompany); // 替换原对象
                return ResponseEntity.ok(updatedCompany);
            }
        }
        return ResponseEntity.notFound().build(); // 没找到该公司
    }
        // 根据 ID 删除公司
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteCompany(@PathVariable long id) {
            boolean removed = companys.removeIf(e -> e.getId() == id);
            if (removed) {
                return ResponseEntity.noContent().build(); // 删除成功，返回 204
            } else {
                return ResponseEntity.notFound().build(); // 没找到该公司，返回 404
            }
        }
}
