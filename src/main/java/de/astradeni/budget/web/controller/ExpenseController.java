package de.astradeni.budget.web.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.astradeni.budget.persistence.models.Category;
import de.astradeni.budget.persistence.models.Expense;
import de.astradeni.budget.persistence.repositories.CategoryRepository;
import de.astradeni.budget.persistence.repositories.ExpenseRepository;
import de.astradeni.budget.services.ExpensesService;


@RestController
@CrossOrigin
public class ExpenseController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ExpensesService expensesService;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/api/expenses")
	public List <ExpenseDto> getAllExpenses() {
		List<Expense> expenses = expensesService.findAll();
		List <ExpenseDto> returnList = expenses.stream().map(expenseEntity -> convertToDto(expenseEntity)).collect(Collectors.toList());
		return returnList;
	}
	@PostMapping("/api/expenses")
	public ResponseEntity<ExpenseDto> insertExpense(@RequestBody ExpenseDto expenseDto) throws ParseException {
		Expense expense = expensesService.insertExpense(convertToEntity(expenseDto));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(expense.getId())
		        .toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/api/expenses/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
		expensesService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	private ExpenseDto convertToDto(Expense expenseEntity) {
		ExpenseDto expenseDto = modelMapper.map(expenseEntity, ExpenseDto.class);
		expenseDto.setCategoryId(expenseEntity.getCategory().getId());
		expenseDto.setCategoryName(expenseEntity.getCategory().getName());
		return expenseDto;
	}
	
	private Expense convertToEntity(ExpenseDto expenseDto) throws ParseException {
		Expense expenseEntity = modelMapper.map(expenseDto, Expense.class);
		Category category = categoryRepository.findById(expenseDto.getCategoryId())
			      .orElseThrow(() -> new CategoryNotFoundException(expenseDto.getCategoryId()));
		expenseEntity.setCategory(category);

	
	    return expenseEntity;
	}
}
