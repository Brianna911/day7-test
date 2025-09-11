package com.example.demo.repository;


import com.example.demo.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryInterface {

    void save(Employee employee);

    void saveOrUpdate(Employee employee);

    Optional<Employee> findById(long id);

    List<Employee> findByGender(String gender);

    boolean deleteById(long id);

    int count();

    List<Employee> findPaged(int start, int end);

    List<Employee> findAll();
}
