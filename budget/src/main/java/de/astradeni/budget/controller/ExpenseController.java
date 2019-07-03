package de.astradeni.budget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.astradeni.budget.models.Expense;
import de.astradeni.budget.repositories.ExpenseDao;

@RestController
public class ExpenseController {
	@Autowired
	private ExpenseDao dao;
	@GetMapping("/expenses")
	public List <Expense> getExpenses() {
		return (List<Expense>) dao.findAll();
	}
}
