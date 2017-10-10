package com.lpa.spring5recipeapp.controllers;

import com.lpa.spring5recipeapp.commands.RecipeCommand;
import com.lpa.spring5recipeapp.domain.Recipe;
import com.lpa.spring5recipeapp.exceptions.NotFoundException;
import com.lpa.spring5recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    private RecipeController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(anyString())).thenReturn(recipe);

        mockMvc.perform(
                get("/recipe/1/show"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/show"))
                    .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        mockMvc.perform(
                get("/recipe/new"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipeform"))
                    .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(
                post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                        .param("directions", "some directions")
                        .param("servings", "4")
                        .param("prepTime", "10")
                        .param("cookTime", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(
                post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("cookTime", "3000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2");

        when(recipeService.findCommandById(anyString())).thenReturn(command);

        mockMvc.perform(
                get("/recipe/1/update"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipeform"))
                    .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(
                get("/recipe/1/delete"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyString());
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(
                get("/recipe/1/show"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("error/404error"));
    }

    @Ignore
    @Test
    public void testGetRecipeBadRequest() throws Exception {
        mockMvc.perform(
                get("/recipe/A/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400error"));
    }
}
