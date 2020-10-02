package com.andersen.converter;

public interface Converter<Entity, Dto> {
    Entity convertToEntity(Dto dto);

    Dto convertToDto(Entity entity);
}
