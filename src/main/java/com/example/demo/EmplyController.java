package com.example.demo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/employees")
public class EmplyController {

    private List<Employee> employees = new ArrayList<>();

    // 创建员工（方式1）
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody Employee employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", employee.getId()));
    }
//
//    // 创建员工（方式2）返回所有员工列表
//    @PostMapping("/2")
//    public ResponseEntity<Map<String, Object>> createEmployee1(@RequestBody Employee employee) {
//        employee.setId(employees.size() + 1);
//        employees.add(employee);
//        Map<String, Object> result = new HashMap<>();
//        result.put("employees", employees);
//        return ResponseEntity.ok(result);
//    }

    // 根据 ID 查询员工
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable long id) {
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 查询所有员工（可按性别过滤）
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Employee>> queryEmployeesByGender(@PathVariable String gender) {
        if (gender == null || gender.isEmpty()) {
            return null;
        }
        List<Employee> filtered = employees.stream()
                .filter(g-> gender.equals(g.getGender()))
                .toList();
        return ResponseEntity.ok(filtered);
    }
    // 根据 ID 删除员工
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        boolean removed = employees.removeIf(e -> e.getId() == id);
        if (removed) {
            return ResponseEntity.noContent().build(); // 删除成功，返回 204
        } else {
            return ResponseEntity.notFound().build(); // 没找到该员工，返回 404
        }
    }


}



