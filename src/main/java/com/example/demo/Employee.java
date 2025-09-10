package com.example.demo;

public class Employee {
    private String name;
    private int age;
    private double salary;
    private int id;
    private String gender;
    boolean active;
    public Employee() {
        super();
    }
    public Employee(String name, int age, double salary, int id) {
        super();
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean b) {
    }
}
