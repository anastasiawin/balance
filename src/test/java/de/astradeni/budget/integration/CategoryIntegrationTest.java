package de.astradeni.budget.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import de.astradeni.budget.BudgetApplication;
import de.astradeni.budget.web.controller.CategoryDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BudgetApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void givenCategories_whenPostCategories_thenStatus200() throws Exception {
    	
        CategoryDto categoryDto = CategoryDto.builder().name("testCategoryName").description("testcategoryDescription").build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(categoryDto);

        MvcResult result = mvc.perform(post("/api/categories")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json))
          .andExpect(status().isCreated())
          .andReturn();
        
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        
        mvc.perform(get("/api/categories/"))
          .andExpect(status().isOk())
                  .andExpect(content().contentType("application/json;charset=UTF-8"))
                  .andExpect(jsonPath("$", hasSize(1)))
                  .andExpect(jsonPath("$[0].id", is(id)))
                  .andExpect(jsonPath("$[0].name", is(categoryDto.getName())))
                  .andExpect(jsonPath("$[0].description", is(categoryDto.getDescription())));
    }
    
    @Test
    @Order(2)
    public void givenCategories_whenGetCategories_thenStatus200() throws Exception {
    	

    }
}
