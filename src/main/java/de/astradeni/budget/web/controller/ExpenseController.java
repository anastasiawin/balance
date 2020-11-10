package de.astradeni.budget.web.controller;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.astradeni.budget.persistence.models.Category;
import de.astradeni.budget.persistence.models.Expense;
import de.astradeni.budget.persistence.repositories.CategoryRepository;
import de.astradeni.budget.persistence.repositories.ExpenseRepository;


@RestController
public class ExpenseController {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/api/expenses")
	public List <ExpenseDto> getAllExpenses() {
		List<Expense> expenses = (List<Expense>) expenseRepository.findAll();
		List <ExpenseDto> returnList = expenses.stream().map(expenseEntity -> convertToDto(expenseEntity)).collect(Collectors.toList());
		return returnList;
	}
	@PostMapping("/api/expenses")
	public Expense insertExpense(@RequestBody ExpenseDto expenseDto) throws ParseException {
		return expenseRepository.save(convertToEntity(expenseDto));
	}
	@GetMapping("/api/categories")
	public List <Category> getAllCategories() {
		return ((List<Category>) categoryRepository.findAll());
	}
	@PostMapping("/api/expenses/{id}")
	public void insertExpense(@PathVariable String id) {
		expenseRepository.deleteById(id);
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
