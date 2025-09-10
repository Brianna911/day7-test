//package com.example.demo.service;
//import com.example.demo.Employee;
//import org.springframework.beans.BeanUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import java.util.*;
//import com.example.demo.repository.EmployeeRepository;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@Service
//public class EmployeeService {
//
//    private final EmployeeRepository employeeRepository;
//
//    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    public Employee createEmployee(Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee, "id");
//
//        employee.setId(employeeRepository.count() + 1);
//        employeeRepository.save(employee);
//
//        return employee;
//    }
//
//    public Employee createEmployeeWithId(long id, Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setId((int) id);
//        employeeRepository.saveOrUpdate(employee);
//        return employee;
//    }
//    public ResponseEntity<Map<String, Object>> createEmployeeV2(Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee, "id");
//
//        employee.setId(employeeRepository.count() + 1);
//        employeeRepository.save(employee);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("employees", employeeRepository.findAll());
//        return ResponseEntity.ok(result);
//    }
////    public ResponseEntity<Employee> getEmployee(long id) {
////        return employeeRepository.findById(id)
////                .map(ResponseEntity::ok)
////                .orElse(ResponseEntity.notFound().build());
////    }
//    public ResponseEntity<List<Employee>> queryEmployeesByGender(String gender) {
//        if (gender == null || gender.isEmpty()) {
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//        return ResponseEntity.ok(employeeRepository.findByGender(gender));
//    }
////    public ResponseEntity<Void> deleteEmployee(long id) {
////        boolean removed = employeeRepository.deleteById(id);
////        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
////    }
//    public ResponseEntity<Map<String, Object>> getEmployees(int page, int size) {
//        int start = (page - 1) * size;
//        int end = start + size;
//
//        List<Employee> pagedEmployees = employeeRepository.findPaged(start, end);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("page", page);
//        result.put("size", size);
//        result.put("total", employeeRepository.count());
//        result.put("employees", pagedEmployees);
//        return ResponseEntity.ok(result);
//    }
//
//    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Employee not found")
//    public class EmployeeNotFoundException extends RuntimeException {
//        public EmployeeNotFoundException(String s) {
//            super(s);
//        }
//    }
//
//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid employee data")
//    public class InvalidEmployeeException extends RuntimeException {
//        public InvalidEmployeeException(String message) {
//            super(message);
//        }
//    }
//
//    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Employee is inactive")
//    public class EmployeeInactiveException extends RuntimeException {
//        public EmployeeInactiveException(String s) {
//            super(s);
//        }
//    }
//
//
////    public Employee createEmployee(Employee employeeDTO) {
////        if (employeeDTO.getAge() < 18 || employeeDTO.getAge() > 65) {
////            throw new InvalidEmployeeException("Employee age must be between 18 and 65.");
////        }
////        if (employeeDTO.getAge() >= 30 && employeeDTO.getSalary() < 20000) {
////            throw new InvalidEmployeeException("Employees over 30 must have salary >= 20000.");
////        }
////
////        Employee employee = new Employee();
////        BeanUtils.copyProperties(employeeDTO, employee, "id");
////        employee.setId(employeeRepository.count() + 1);
////        employeeRepository.save(employee);
////        return employee;
////    }
//
//    public ResponseEntity<Employee> getEmployee(long id) {
//        return employeeRepository.findById(id)
//                .map(ResponseEntity::ok)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
//    }
//
//    public ResponseEntity<Employee> updateEmployee(long id, Employee employeeDTO) {
//        Employee existing = employeeRepository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
//
//        if (existing!=null||existing.getId()!=0) {
//            throw new EmployeeInactiveException("Cannot update inactive employee.");
//        }
//
//        BeanUtils.copyProperties(employeeDTO, existing, "id");
//        employeeRepository.saveOrUpdate(existing);
//        return ResponseEntity.ok(existing);
//    }
//
//    public ResponseEntity<Void> deleteEmployee(long id) {
//        boolean removed = employeeRepository.deleteById(id);
//        if (!removed) {
//            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
//        }
//        return ResponseEntity.noContent().build();
//    }
//
//}

package com.example.demo.service;

import com.example.demo.Employee;
import com.example.demo.exception.EmployeeInactiveException;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.exception.InvalidEmployeeException;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employeeDTO) {
        if (employeeDTO.getAge() < 18 || employeeDTO.getAge() > 65) {
            throw new InvalidEmployeeException("Employee age must be between 18 and 65.");
        }
        if (employeeDTO.getAge() >= 30 && employeeDTO.getSalary() < 20000) {
            throw new InvalidEmployeeException("Employees over 30 must have salary >= 20000.");
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setId(employeeRepository.count() + 1);
        employeeRepository.save(employee);
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
        Employee employee = createEmployee(employeeDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("employees", employeeRepository.findAll());
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Employee> getEmployee(long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));
    }

    public ResponseEntity<Employee> updateEmployee(long id, Employee employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found."));

        if (!existing.isActive()) {
            throw new EmployeeInactiveException("Cannot update inactive employee.");
        }

        BeanUtils.copyProperties(employeeDTO, existing, "id");
        employeeRepository.saveOrUpdate(existing);
        return ResponseEntity.ok(existing);
    }

    public ResponseEntity<Void> deleteEmployee(long id) {
        boolean removed = employeeRepository.deleteById(id);
        if (!removed) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<Employee>> queryEmployeesByGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(employeeRepository.findByGender(gender));
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
