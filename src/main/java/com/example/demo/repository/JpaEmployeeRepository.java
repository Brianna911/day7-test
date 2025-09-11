package com.example.demo.repository;
import com.example.demo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByGender(String gender);
}
