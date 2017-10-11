package com.lpa.spring5recipeapp.repositories.reactive;

import com.lpa.spring5recipeapp.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
