package com.example.demo.repository;
import com.example.demo.Employee;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.Employee;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;
@Repository
//public class EmployeeRepository {
//
//    private final List<Employee> employees = new ArrayList<>();
//
//    public void save(Employee employee) {
//        employees.add(employee);
//    }
//
//    public void saveOrUpdate(Employee employee) {
//        employees.removeIf(e -> e.getId() == employee.getId());
//        employees.add(employee);
//    }
//
//    public Optional<Employee> findById(long id) {
//        return employees.stream().filter(e -> e.getId() == id).findFirst();
//    }
//
//    public List<Employee> findByGender(String gender) {
//        return employees.stream()
//                .filter(e -> gender.equals(e.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    public boolean deleteById(long id) {
//        return employees.removeIf(e -> e.getId() == id);
//    }
//
//    public int count() {
//        return employees.size();
//    }
//
//    public List<Employee> findPaged(int start, int end) {
//        if (start >= employees.size()) return Collections.emptyList();
//        return employees.subList(start, Math.min(end, employees.size()));
//    }
//
//    public List<Employee> findAll() {
//        return new ArrayList<>(employees);
//    }
//}
//public class EmployeeRepository implements EmployeeRepositoryInterface {
//
//    private final List<Employee> employees = new ArrayList<>();
//
//    @Override
//    public void save(Employee employee) {
//        employees.add(employee);
//    }
//
//    @Override
//    public void saveOrUpdate(Employee employee) {
//        employees.removeIf(e -> e.getId() == employee.getId());
//        employees.add(employee);
//    }
//
//    @Override
//    public Optional<Employee> findById(long id) {
//        return employees.stream().filter(e -> e.getId() == id).findFirst();
//    }
//
//    @Override
//    public List<Employee> findByGender(String gender) {
//        return employees.stream()
//                .filter(e -> gender.equals(e.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean deleteById(long id) {
//        return employees.removeIf(e -> e.getId() == id);
//    }
//
//    @Override
//    public int count() {
//        return employees.size();
//    }
//
//    @Override
//    public List<Employee> findPaged(int start, int end) {
//        if (start >= employees.size()) return Collections.emptyList();
//        return employees.subList(start, Math.min(end, employees.size()));
//    }
//
//    @Override
//    public List<Employee> findAll() {
//        return new ArrayList<>(employees);
//    }
//
//}
// 可用于 @Qualifier 注入
public class EmployeeRepository implements EmployeeRepositoryInterface {

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void save(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void saveOrUpdate(Employee employee) {
        employees.removeIf(e -> e.getId() == employee.getId());
        employees.add(employee);
    }

    @Override
    public Optional<Employee> findById(long id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst();
    }

    @Override
    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(e -> gender.equals(e.getGender()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long id) {
        return employees.removeIf(e -> e.getId() == id);
    }

    @Override
    public int count() {
        return employees.size();
    }

    @Override
    public List<Employee> findPaged(int start, int end) {
        if (start >= employees.size()) return Collections.emptyList();
        return employees.subList(start, Math.min(end, employees.size()));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }
}
