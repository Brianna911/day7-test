package com.example.demo;

import java.util.List;

public class Company {
    private String name;
    private int id;

    private List<Employee> employees; // 一对多关系

    public Company() {
        super();
    }
    public Company(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
