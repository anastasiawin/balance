package de.astradeni.budget.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDto {

	private String id;
	
	@NotNull
	private String name;
	
	private String description;
	
	@NotNull
	private BigDecimal total;
	
	private String categoryId;
	
	private String categoryName;
	
	private LocalDate date;

}

