package de.astradeni.budget.repositories;

import org.springframework.data.repository.CrudRepository;

import de.astradeni.budget.models.Expense;

public interface ExpenseDao extends CrudRepository<Expense, Long> {
	

}
