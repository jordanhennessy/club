package com.jordan.club.common.service;

import java.util.List;

public interface CommonService<T> {
    List<T> getAll();
    T getById(Long id);
    T save(T newDTO);
    T update(Long id, T updatedDTO);
    void delete(Long id);
}
