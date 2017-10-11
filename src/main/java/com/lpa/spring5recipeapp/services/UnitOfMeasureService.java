package com.lpa.spring5recipeapp.services;

import com.lpa.spring5recipeapp.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUoms();
}
