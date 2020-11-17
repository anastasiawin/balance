package de.astradeni.budget.web.controller;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.astradeni.budget.persistence.models.Category;
import de.astradeni.budget.persistence.repositories.CategoryRepository;
import de.astradeni.budget.services.CategoriesService;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoriesService categoriesService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/api/categories")
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = (List<Category>) categoryRepository.findAll();
		return categories.stream().map(categoryEntity -> convertToDto(categoryEntity)).collect(Collectors.toList());
	}

	@PostMapping("/api/categories")
	public ResponseEntity<CategoryDto> insertExpenseCategories(@RequestBody CategoryDto categoryDto) throws ParseException {
		Category category = categoriesService.insertCategory(convertToEntity(categoryDto));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId())
		        .toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/api/categories/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
		categoriesService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	private CategoryDto convertToDto(Category categoryEntity) {
		return modelMapper.map(categoryEntity, CategoryDto.class);
	}

	private Category convertToEntity(CategoryDto categoryDto) throws ParseException {
		return modelMapper.map(categoryDto, Category.class);
	}
}
