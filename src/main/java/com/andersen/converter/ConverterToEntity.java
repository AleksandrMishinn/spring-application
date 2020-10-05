package com.andersen.converter;

public interface ConverterToEntity<Dto, Entity> {
    Entity convertToEntity(Dto dto);
}
