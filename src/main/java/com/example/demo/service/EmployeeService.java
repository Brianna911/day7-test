package com.example.demo.service;

import com.example.demo.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;


//@Service
//public class EmployeeService {
//
//    private final List<Employee> employees = new ArrayList<>();
//
//    public ResponseEntity<Employee> createEmployee(Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee, "id");
//        employee.setId(employees.size() + 1);
//        employees.add(employee);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(employee.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).body(employee);
//    }
//
//    public ResponseEntity<Employee> createEmployeeWithId(long id, Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setId((int) id);
//        employees.removeIf(e -> e.getId() == id);
//        employees.add(employee);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequestUri()
//                .build()
//                .toUri();
//
//        return ResponseEntity.created(location).body(employee);
//    }
//
//    public ResponseEntity<Map<String, Object>> createEmployeeV2(Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee, "id");
//        employee.setId(employees.size() + 1);
//        employees.add(employee);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("employees", new ArrayList<>(employees));
//        return ResponseEntity.ok(result);
//    }
//
//    public ResponseEntity<Employee> getEmployee(long id) {
//        return employees.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    public ResponseEntity<List<Employee>> queryEmployeesByGender(String gender) {
//        if (gender == null || gender.isEmpty()) {
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//        List<Employee> filtered = employees.stream()
//                .filter(g -> gender.equals(g.getGender()))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(filtered);
//    }
//
//    public ResponseEntity<Void> deleteEmployee(long id) {
//        boolean removed = employees.removeIf(e -> e.getId() == id);
//        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }
//
//    public ResponseEntity<Map<String, Object>> getEmployees(int page, int size) {
//        int start = (page - 1) * size;
//        int end = Math.min(start + size, employees.size());
//
//        List<Employee> pagedEmployees = new ArrayList<>();
//        if (start < employees.size()) {
//            pagedEmployees = employees.subList(start, end);
//        }
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("page", page);
//        result.put("size", size);
//        result.put("total", employees.size());
//        result.put("employees", pagedEmployees);
//
//        return ResponseEntity.ok(result);
//    }
//}
import com.example.demo.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "id");

        employee.setId(employeeRepository.count() + 1);
        employeeRepository.save(employee);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(employee.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(employee);

        return employee;
    }

    public Employee createEmployeeWithId(long id, Employee employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setId((int) id);
        employeeRepository.saveOrUpdate(employee);
        return employee;
    }

    public ResponseEntity<Map<String, Object>> createEmployeeV2(Employee employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "id");

        employee.setId(employeeRepository.count() + 1);
        employeeRepository.save(employee);

        Map<String, Object> result = new HashMap<>();
        result.put("employees", employeeRepository.findAll());
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Employee> getEmployee(long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Employee>> queryEmployeesByGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(employeeRepository.findByGender(gender));
    }

    public ResponseEntity<Void> deleteEmployee(long id) {
        boolean removed = employeeRepository.deleteById(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Map<String, Object>> getEmployees(int page, int size) {
        int start = (page - 1) * size;
        int end = start + size;

        List<Employee> pagedEmployees = employeeRepository.findPaged(start, end);

        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("size", size);
        result.put("total", employeeRepository.count());
        result.put("employees", pagedEmployees);
        return ResponseEntity.ok(result);
    }
}
