package com.jordan.club.common.mapper;

public interface CommonMapper<T,S> {
    T fromDTO(S dto);
    S toDTO(T entity);
}
