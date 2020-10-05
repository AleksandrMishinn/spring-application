package com.andersen.converter;

public interface ConverterToDto<Entity, Dto> {
    Dto convertToDto(Entity entity);
}
