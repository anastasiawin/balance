package de.astradeni.budget.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.astradeni.budget.persistence.models.Category;
import de.astradeni.budget.persistence.repositories.CategoryRepository;

@Service
public class CategoriesService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll() {
	    return (List<Category>) categoryRepository.findAll();
	}
	
	public Category insertCategory (Category category) {
		return categoryRepository.save(category);
	  }
	
	public void deleteById(String id) {
		categoryRepository.deleteById(id);
	  }
}
