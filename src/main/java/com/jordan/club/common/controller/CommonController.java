package com.jordan.club.common.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommonController<T> {
    ResponseEntity<List<T>> getAll();
    ResponseEntity<T> getById(Long id);
    ResponseEntity<T> create(T newDTO);
    ResponseEntity<T> update(Long id, T updatedDTO);
    ResponseEntity<String> delete(Long id);
}
