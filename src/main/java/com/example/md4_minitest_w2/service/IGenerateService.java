package com.example.md4_minitest_w2.service;

import java.util.Optional;

public interface IGenerateService<T>{
    Iterable<T> findAll();
    void save(T t);

    void update(T t);
    T findById(Long id) throws Exception;
    void remove(Long id);
}
