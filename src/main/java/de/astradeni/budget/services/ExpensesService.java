package de.astradeni.budget.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.astradeni.budget.persistence.models.Expense;
import de.astradeni.budget.persistence.repositories.ExpenseRepository;

@Service
public class ExpensesService {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	public List<Expense> findAll() {
	    return (List<Expense>) expenseRepository.findAll();
	}
	
	public Expense insertExpense (Expense expense) {
		return expenseRepository.save(expense);
	  }
	
	public void deleteById(String id) {
		expenseRepository.deleteById(id);
	  }
}
