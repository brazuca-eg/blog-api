package com.kravchenko.blogapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GeneralCrudController<T> {
    ResponseEntity<T> create(@RequestBody T obj);

    ResponseEntity<List<T>> getAll();

    ResponseEntity<T> getById(@PathVariable String id);

}