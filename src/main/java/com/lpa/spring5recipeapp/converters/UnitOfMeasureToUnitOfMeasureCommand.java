package com.lpa.spring5recipeapp.converters;

import com.lpa.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.lpa.spring5recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if(source == null) {
            return null;
        }

        final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();

        uomc.setId(source.getId());
        uomc.setDescription(source.getDescription());

        return uomc;
    }
}
