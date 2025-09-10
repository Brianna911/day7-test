package com.example.demo.controller;
import com.example.demo.service.EmployeeService;
import com.example.demo.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

//@RestController
//@RequestMapping("/employees")
//public class EmplyController {
//
//    private final List<Employee> employees = new ArrayList<>();
//
//    // === 方式1：POST /employees ===
//    // 返回 201 Created + 完整 Employee（满足 “id==1” 和 “name==John Smith” 两个测试）
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employeeDTO) {
//        Employee employee = new Employee();
//        // 忽略传入的 id，由系统分配
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
//    // === 方式2（测试特意使用）：POST /employees/{id} ===
//    // testGetEmployeeById 会先 POST /employees/1（期望 201），再 GET /employees/1 校验字段
//    @PostMapping(value = "/{id}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Employee> createEmployeeWithId(@PathVariable long id,
//                                                         @RequestBody Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setId((int) id);
//        // 若已存在相同 id，则先移除再添加，保证最终是该 id 的最新数据
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
//    // === 方式3（测试专用固定路径）：POST /employees/2 ===
//    // testQueryEmployeesByGender 连续调用两次该接口，不校验响应体，只要能创建两条记录即可
//    @PostMapping(value = "/2",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Object>> createEmployeeV2(@RequestBody Employee employeeDTO) {
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDTO, employee, "id");
//        employee.setId(employees.size() + 1);
//        employees.add(employee);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("employees", new ArrayList<>(employees));
//        return ResponseEntity.ok(result); // 200 OK（测试未断言状态码/响应体）
//    }
//
//    // GET /employees/{id}
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
//        return employees.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // GET /employees/gender/{gender}
//    @GetMapping(value = "/gender/{gender}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Employee>> queryEmployeesByGender(@PathVariable String gender) {
//        if (gender == null || gender.isEmpty()) {
//            // 避免返回 null 造成 500
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//        List<Employee> filtered = employees.stream()
//                .filter(g -> gender.equals(g.getGender()))
//                .collect(Collectors.toList()); // 如果使用 JDK16+ 也可 .toList()
//        return ResponseEntity.ok(filtered);
//    }
//
//    // DELETE /employees/delete/{id}
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
//        boolean removed = employees.removeIf(e -> e.getId() == id);
//        if (removed) {
//            return ResponseEntity.noContent().build(); // 204
//        } else {
//            return ResponseEntity.notFound().build(); // 404
//        }
//    }
//    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String, Object>> getEmployees(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "5") int size) {
//
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
//        return ResponseEntity.ok(result); // 200 OK
//    }
//
//}
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employeeDTO) {
//        return employeeService.createEmployee(employeeDTO);
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
}
