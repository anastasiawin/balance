package de.astradeni.budget.web.controller;

public class CategoryNotFoundException extends RuntimeException {

	CategoryNotFoundException(Long id) {
	    super("Could not find category " + id);
	  }

}
