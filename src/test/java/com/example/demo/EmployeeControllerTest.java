package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Employee testEmployee;
    @BeforeEach
    void setup() {
        testEmployee = new Employee();
        testEmployee.setName("John Smith");
        testEmployee.setAge(30);
        testEmployee.setGender("MALE");
        testEmployee.setSalary(60000);
        testEmployee.setActive(true);
        testEmployee.setId(456);
    }

    @Test
    public void testCreateEmployee1() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testCreateEmployeeAndReturn() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Smith"));
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        mockMvc.perform(post("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Smith"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.salary").value(60000));
    }

    @Test
    public void testQueryEmployeesByGender() throws Exception {
        Employee femaleEmployee = new Employee();
        femaleEmployee.setName("Jane Doe");
        femaleEmployee.setAge(30);
        femaleEmployee.setGender("FEMALE");
        femaleEmployee.setSalary(55000);

        mockMvc.perform(post("/employees/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testEmployee)));

        mockMvc.perform(post("/employees/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(femaleEmployee)));

        mockMvc.perform(get("/employees/gender/MALE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Smith"));
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        // 创建员工
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated());

        // 删除员工
        mockMvc.perform(delete("/employees/delete/1"))
                .andExpect(status().isNoContent());

        // 再次尝试获取该员工，验证是否已删除
        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetEmployeesWithPagination() throws Exception {
        // 创建多个员工
        for (int i = 1; i <= 10; i++) {
            Employee employee = new Employee();
            employee.setName("Employee " + i);
            employee.setSalary(i * 20000);
            employee.setAge(i + 18);
            employee.setId(i);

            mockMvc.perform(post("/employees")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(employee)))
                    .andExpect(status().isCreated());
        }

        // 分页查询第一页，大小为5
        mockMvc.perform(get("/employees/employees?page=1&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(5))
                .andExpect(jsonPath("$.employees.length()").value(5));
    }
}
