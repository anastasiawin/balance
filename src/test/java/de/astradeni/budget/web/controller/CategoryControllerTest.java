package de.astradeni.budget.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.astradeni.budget.persistence.models.Category;
import de.astradeni.budget.persistence.repositories.CategoryRepository;
import de.astradeni.budget.services.CategoriesService;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

	@Autowired
    private MockMvc mvc;

	@MockBean
	private CategoriesService categoriesService;
		
    @MockBean
    private CategoryRepository repository;
    
    @Test
    public void givenCategories_whenGetAllCategories_thenReturnAllCategories()
      throws Exception {
        
        Category category1 = new Category("1", "category1", "description1");
        Category category2 = new Category("2", "category2", "description2");
        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        given(repository.findAll()).willReturn(categories);

        mvc.perform(get("/api/categories")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].id", is("1")))
          .andExpect(jsonPath("$[0].name", is("category1")))
          .andExpect(jsonPath("$[0].description", is("description1")))
          .andExpect(jsonPath("$[1].id", is("2")))
          .andExpect(jsonPath("$[1].name", is("category2")))
          .andExpect(jsonPath("$[1].description", is("description2")));
    }
    
    @Test
    public void whenInsertExpenseCategory_thenInsertCategory()
      throws Exception {
        
        Category category = new Category("1", "category1", "description1");
        Category expectedCategory = new Category(null, "category1", "description1");
        given(categoriesService.insertCategory(expectedCategory)).willReturn(category);
        CategoryDto dto = new CategoryDto(null, "category1", "description1");
        
        mvc.perform(post("/api/categories").contentType(MediaType.APPLICATION_JSON)
        		.content(toJson(dto))).andExpect(status().isCreated());
        
        verify(categoriesService, VerificationModeFactory.times(1)).insertCategory(expectedCategory);
        reset(categoriesService);
        
    }
    
    @Test
    public void whenDeleteCategory_thenDeleteCategory()
      throws Exception {
       
        mvc.perform(delete("/api/categories/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        
        verify(categoriesService, VerificationModeFactory.times(1)).deleteById("1");
        reset(categoriesService);
    }
    
    private byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
