package de.astradeni.budget.web.controller;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

	private String id;
	
	@NotNull
	private String name;
	
	private String description;
	
}

