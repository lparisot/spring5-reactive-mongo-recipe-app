package com.lpa.spring5recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand unitOfMeasure;
}
