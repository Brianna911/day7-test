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
public class CompanyCotrollerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Company testCompany;

    @BeforeEach
    void setup() {
        testCompany = new Company();
        testCompany.setName("Company");
        testCompany.setId(1);
    }

    @Test
    public void testCreateCompany1() throws Exception {
        mockMvc.perform(post("/companys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    public void testGetCompanyById() throws Exception {
        mockMvc.perform(post("/companys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCompany)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/companys/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company"))
                .andExpect(jsonPath("$.id").value(1));
    }



    @Test
    public void testDeleteCompanyById() throws Exception {
        // 创建公司
        mockMvc.perform(post("/companys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCompany)))
                .andExpect(status().isCreated());

        // 删除公司
        mockMvc.perform(delete("/companys/delete/1"))
                .andExpect(status().isNoContent());

        // 再次尝试获取该公司，验证是否已删除
        mockMvc.perform(get("/companys/1"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateCompanyById() throws Exception {
        // 创建公司
        mockMvc.perform(post("/companys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCompany)))
                .andExpect(status().isCreated());

        // 构造更新后的公司对象
        Company updatedCompany = new Company();
        updatedCompany.setName("Updated Company Name");
        updatedCompany.setId(1); // 保持原 ID 不变

        // 更新公司
        mockMvc.perform(put("/companys/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCompany)))
                .andExpect(status().isOk());

        // 获取更新后的公司，验证是否更新成功
        mockMvc.perform(get("/companys/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Company Name"));
    }

}
