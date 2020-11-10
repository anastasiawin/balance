package de.astradeni.budget.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import de.astradeni.budget.persistence.models.Category;

public interface CategoryRepository extends CrudRepository<Category, String> {
	

}
