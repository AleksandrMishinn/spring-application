package com.andersen.converter;

public interface Converter<S,T> {
    public T convert(S source);
}
