package com.example.demo.service;

import com.example.demo.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Test
    public void testCreateEmployee() {
        // Arrange
        Employee input = new Employee();
        input.setName("John Smith");
        input.setGender("MALE");
        input.setAge(30);
        input.setSalary(60000);

        // 模拟 repository.count() 返回 0
        when(employeeRepository.count()).thenReturn(0);
        Employee employee = employeeService.createEmployee(input);

        assertNotNull(employee);
        assertEquals(1, employee.getId());
        assertEquals("John Smith", employee.getName());

        // 验证 save 被调用
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }


@Test
public void testCreateEmployeeWithId() {
    Employee input = new Employee();
    input.setName("Jane Doe");
    input.setGender("FEMALE");
    input.setAge(28);
    input.setSalary(55000);
    Employee employee = employeeService.createEmployeeWithId(5, input);

    assertEquals(5, employee.getId());
    assertEquals("Jane Doe", employee.getName());
    verify(employeeRepository, times(1)).saveOrUpdate(any(Employee.class));
}

@Test
public void testCreateEmployeeV2() {
    Employee input = new Employee();
    input.setName("Alice");
    input.setGender("FEMALE");
    input.setAge(26);
    input.setSalary(52000);

    when(employeeRepository.count()).thenReturn(1);
    when(employeeRepository.findAll()).thenReturn(List.of(input));

    ResponseEntity<Map<String, Object>> response = employeeService.createEmployeeV2(input);

    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().containsKey("employees"));
    verify(employeeRepository, times(1)).save(any(Employee.class));
}

@Test
public void testGetEmployeeFound() {
    Employee employee = new Employee();
    employee.setId(1);
    employee.setName("John");

    when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

    ResponseEntity<Employee> response = employeeService.getEmployee(1);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals("John", response.getBody().getName());
}

@Test
public void testGetEmployeeNotFound() {
    when(employeeRepository.findById(99)).thenReturn(Optional.empty());

    ResponseEntity<Employee> response = employeeService.getEmployee(99);

    assertEquals(404, response.getStatusCodeValue());
}

@Test
public void testQueryEmployeesByGender() {
    Employee e1 = new Employee();
    e1.setName("Tom");
    e1.setGender("MALE");

    when(employeeRepository.findByGender("MALE")).thenReturn(List.of(e1));

    ResponseEntity<List<Employee>> response = employeeService.queryEmployeesByGender("MALE");

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(1, response.getBody().size());
    assertEquals("Tom", response.getBody().get(0).getName());
}

@Test
public void testQueryEmployeesByGenderEmpty() {
    ResponseEntity<List<Employee>> response = employeeService.queryEmployeesByGender("");

    assertEquals(200, response.getStatusCodeValue());
    assertTrue(response.getBody().isEmpty());
}

@Test
public void testDeleteEmployeeSuccess() {
    when(employeeRepository.deleteById(1)).thenReturn(true);

    ResponseEntity<Void> response = employeeService.deleteEmployee(1);

    assertEquals(204, response.getStatusCodeValue());
}

@Test
public void testDeleteEmployeeNotFound() {
    when(employeeRepository.deleteById(99)).thenReturn(false);

    ResponseEntity<Void> response = employeeService.deleteEmployee(99);

    assertEquals(404, response.getStatusCodeValue());
}

@Test
public void testGetEmployeesWithPagination() {
    List<Employee> mockList = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
        Employee e = new Employee();
        e.setId(i);
        e.setName("Emp " + i);
        mockList.add(e);
    }

    when(employeeRepository.findPaged(0, 5)).thenReturn(mockList);
    when(employeeRepository.count()).thenReturn(10);

    ResponseEntity<Map<String, Object>> response = employeeService.getEmployees(1, 5);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(5, ((List<?>) response.getBody().get("employees")).size());
    assertEquals(10, response.getBody().get("total"));
}
}
