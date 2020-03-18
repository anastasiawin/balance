package de.astradeni.budget.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.astradeni.budget.persistence.models.Category;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends CrudRepository<Category, Long> {
	

}
