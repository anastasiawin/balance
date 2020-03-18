package de.astradeni.budget.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ExpenseDto {

	private long id;
	
	@NotNull
	private String name;
	
	private String description;
	
	@NotNull
	private BigDecimal total;
	
	private long categoryId;
	
	private LocalDate date;

}

