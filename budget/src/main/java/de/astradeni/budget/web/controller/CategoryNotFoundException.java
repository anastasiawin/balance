package de.astradeni.budget.web.controller;

public class CategoryNotFoundException extends RuntimeException {

	CategoryNotFoundException(String id) {
	    super("Could not find category " + id);
	  }

}
