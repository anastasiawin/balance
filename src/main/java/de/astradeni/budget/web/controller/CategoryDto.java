package de.astradeni.budget.web.controller;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CategoryDto {

	private String id;
	
	@NotNull
	private String name;
	
	private String description;
	
}

