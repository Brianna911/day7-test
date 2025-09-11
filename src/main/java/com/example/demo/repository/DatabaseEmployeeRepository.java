package com.example.demo.repository;



import com.example.demo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabaseEmployeeRepository implements EmployeeRepositoryInterface {

    private final JpaEmployeeRepository jpaRepository;

    @Autowired
    public DatabaseEmployeeRepository(JpaEmployeeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Employee employee) {
        jpaRepository.save(employee);
    }

    @Override
    public void saveOrUpdate(Employee employee) {
        jpaRepository.save(employee); // JPA 的 save 方法自动处理 insert/update
    }

    @Override
    public Optional<Employee> findById(long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Employee> findByGender(String gender) {
        return jpaRepository.findByGender(gender);
    }

    @Override
    public boolean deleteById(long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public int count() {
        return (int) jpaRepository.count();
    }

    @Override
    public List<Employee> findPaged(int start, int end) {
        List<Employee> all = jpaRepository.findAll();
        if (start >= all.size()) return List.of();
        return all.subList(start, Math.min(end, all.size()));
    }

    @Override
    public List<Employee> findAll() {
        return jpaRepository.findAll();
    }

}
