package com.kravchenko.blogapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public abstract class AbstractService<T, ID, R extends MongoRepository<T, ID>> {
    @Autowired
    protected R repository;

    public T create(T entity) {
        return repository.save(entity);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T getById(ID id) {
        return repository.findById(id).orElse(null);
    }

    public T update(T entity) {
        return repository.save(entity);
    }
}
