package com.lpa.spring5recipeapp.config;

import com.lpa.spring5recipeapp.domain.Recipe;
import com.lpa.spring5recipeapp.domain.UnitOfMeasure;
import com.lpa.spring5recipeapp.services.RecipeService;
import com.lpa.spring5recipeapp.services.UnitOfMeasureService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class WebConfig {
    @Bean
    public RouterFunction<?> routes(RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        return RouterFunctions
                    .route(GET("/api/recipes"),
                        serverRequest -> ServerResponse
                                            .ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .body(recipeService.getRecipes(), Recipe.class))
                .and(RouterFunctions.route(GET("/api/uoms"),
                        serverRequest -> ServerResponse
                                .ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(unitOfMeasureService.getUnitOfMeasures(), UnitOfMeasure.class)));
    }
}
