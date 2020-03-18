package de.astradeni.budget.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import de.astradeni.budget.persistence.models.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
	

}
