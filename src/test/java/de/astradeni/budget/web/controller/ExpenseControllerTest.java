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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import de.astradeni.budget.persistence.models.Expense;
import de.astradeni.budget.persistence.repositories.CategoryRepository;
import de.astradeni.budget.persistence.repositories.ExpenseRepository;
import de.astradeni.budget.services.ExpensesService;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

	@Autowired
    private MockMvc mvc;

	@MockBean
	private ExpenseRepository expensesRepository;
		
	@MockBean
	private ExpensesService expensesService;
	
    @MockBean
    private CategoryRepository categoryRepository;
    
    @Test
    public void givenExpenses_whenGetAllExpenses_thenReturnAllExpenses()
      throws Exception {
    	LocalDate date = LocalDate.now();
        Category category = new Category("1", "testCategory", "testDescription");
		Expense expense1 = Expense.builder().id("1").category(category)
				.date(LocalDate.now()).description("description1").name("name1").total(BigDecimal.valueOf(123)).build();
		Expense expense2 = Expense.builder().id("2").category(category)
				.date(LocalDate.now()).description("description2").name("name2").total(BigDecimal.valueOf(432)).build();
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense1);
        expenses.add(expense2);

        given(expensesService.findAll()).willReturn(expenses);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateString = date.format(formatter);
        
        mvc.perform(get("/api/expenses")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].id", is("1")))
          .andExpect(jsonPath("$[0].name", is("name1")))
          .andExpect(jsonPath("$[0].description", is("description1")))
          .andExpect(jsonPath("$[0].total", is(123)))
          .andExpect(jsonPath("$[0].categoryId", is("1")))
          .andExpect(jsonPath("$[0].categoryName", is("testCategory")))
          .andExpect(jsonPath("$[0].date", is(formattedDateString)))
          .andExpect(jsonPath("$[1].id", is("2")))
          .andExpect(jsonPath("$[1].name", is("name2")))
          .andExpect(jsonPath("$[1].description", is("description2")))
          .andExpect(jsonPath("$[1].total", is(432)))
          .andExpect(jsonPath("$[1].categoryId", is("1")))
          .andExpect(jsonPath("$[1].categoryName", is("testCategory")))
          .andExpect(jsonPath("$[1].date", is(formattedDateString)));
    }
    

    @Test
    public void whenInsertExpense_thenExpenseInserted()
      throws Exception {
        
        Category category = new Category("1", "category1", "description1");
		Expense expense = Expense.builder().category(category)
				.date(LocalDate.now()).description("description1").name("name1").total(BigDecimal.valueOf(123)).build();
		Expense expectedExpense = Expense.builder().id("1").category(category)
				.date(LocalDate.now()).description("description1").name("name1").total(BigDecimal.valueOf(123)).build();

        given(expensesService.insertExpense(expense)).willReturn(expectedExpense);
        
        given(categoryRepository.findById("1")).willReturn(Optional.of(category));
        
		ExpenseDto expenseDto = ExpenseDto.builder().categoryId("1")
				.date(LocalDate.now()).description("description1").name("name1").total(BigDecimal.valueOf(123)).build();
        
        mvc.perform(post("/api/expenses").contentType(MediaType.APPLICATION_JSON)
        		.content(toJson(expenseDto))).andExpect(status().isCreated());

        verify(expensesService, VerificationModeFactory.times(1)).insertExpense(expense);
        verify(categoryRepository, VerificationModeFactory.times(1)).findById("1");
        reset(expensesService);
        reset(categoryRepository);
        
    }
    
    @Test
    public void whenDeleteCategory_thenDeleteCategory()
      throws Exception {
       
        mvc.perform(delete("/api/expenses/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        
        verify(expensesService, VerificationModeFactory.times(1)).deleteById("1");
        reset(expensesService);
    }
    
    private byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
