package com.lpa.spring5recipeapp.controllers;

import com.lpa.spring5recipeapp.commands.IngredientCommand;
import com.lpa.spring5recipeapp.commands.RecipeCommand;
import com.lpa.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.lpa.spring5recipeapp.services.IngredientService;
import com.lpa.spring5recipeapp.services.RecipeService;
import com.lpa.spring5recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Controller
public class IngredientController {
    private static final String RECIPE_INGREDIENTFORM_URL = "recipe/ingredient/ingredientform";

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private WebDataBinder webDataBinder;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(Model model, @PathVariable String recipeId) {
        log.debug("Getting ingredient list for recipe id: " + recipeId);

        // use command object to avoid lazy load errors in Thymeleaf.
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(Model model,
                                       @PathVariable String recipeId,
                                       @PathVariable String ingredientId) {
        log.debug("Getting ingredient " + ingredientId + " for recipe " + recipeId);

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(Model model,
                                         @PathVariable String recipeId,
                                         @PathVariable String ingredientId) {
        log.debug("Update ingredient " + ingredientId + " for recipe " + recipeId);

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());

        model.addAttribute("uoms", unitOfMeasureService.listAllUoms());

        return RECIPE_INGREDIENTFORM_URL;
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(Model model, @ModelAttribute("ingredient") IngredientCommand command, @PathVariable String recipeId) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach((objectError -> {
                log.debug(objectError.toString());
            }));

            model.addAttribute("ingredient", command);

            model.addAttribute("uoms", unitOfMeasureService.listAllUoms());

            return RECIPE_INGREDIENTFORM_URL;
        }

        command.setRecipeId(recipeId);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipe(Model model, @PathVariable String recipeId) {

        //make sure we have a good id value
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
        //todo raise exception if null

        //need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(UUID.randomUUID().toString());
        ingredientCommand.setRecipeId(recipeId);
        //init uom
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);

        model.addAttribute("uoms", unitOfMeasureService.listAllUoms());

        return RECIPE_INGREDIENTFORM_URL;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId) {
        log.debug("deleting ingredient id:" + ingredientId);

        ingredientService.deleteById(recipeId, ingredientId).block();

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
