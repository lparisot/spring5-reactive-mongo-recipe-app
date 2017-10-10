package com.lpa.spring5recipeapp.services;

import com.lpa.spring5recipeapp.commands.RecipeCommand;
import com.lpa.spring5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String id);

    void deleteById(String idToDelete);
}
