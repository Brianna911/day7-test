package com.example.demo.controller;
import com.example.demo.controller.dto.EmployeeRequest;
import com.example.demo.repository.EmployeeRepositoryInterface;
import com.example.demo.service.EmployeeService;
import com.example.demo.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import static org.springframework.http.HttpStatus.CREATED;
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Qualifier("employeeRepository") // 使用内存实现
    private EmployeeRepositoryInterface employeeRepository;

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employeeDTO) {
        Employee employee = employeeService.createEmployee(employeeDTO);

        return ResponseEntity.status(CREATED).body(employee);
    }

    @PostMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployeeWithId(@PathVariable long id,
                                                         @RequestBody Employee employeeDTO) {
        return ResponseEntity.status(CREATED).body(employeeService.createEmployeeWithId(id, employeeDTO)) ;
    }

    @PostMapping(value = "/2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createEmployeeV2(@RequestBody Employee employeeDTO) {
        return employeeService.createEmployeeV2(employeeDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping(value = "/gender/{gender}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> queryEmployeesByGender(@PathVariable String gender) {
        return employeeService.queryEmployeesByGender(gender);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping(value = "/employees",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return employeeService.getEmployees(page, size);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id,
                                                   @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.updateEmployee(id, employeeRequest);
    }
}
