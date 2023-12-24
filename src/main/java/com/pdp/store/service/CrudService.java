package com.pdp.store.service;

import java.util.List;

public interface CrudService<T> {
    T add(T t);

    List<T> getAll();

    T getById(Long id);

    void deleteById(Long id);

    void update(Long id, T updatedEntity);
}
