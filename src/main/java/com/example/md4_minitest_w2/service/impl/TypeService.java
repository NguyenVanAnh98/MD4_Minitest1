package com.example.md4_minitest_w2.service.impl;

import com.example.md4_minitest_w2.model.Type;
import com.example.md4_minitest_w2.repo.ITypeRepo;
import com.example.md4_minitest_w2.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class TypeService implements ITypeService {
    @Autowired
    private ITypeRepo iTypeRepo;
    @Override
    public Iterable<Type> findAll() {
        return iTypeRepo.findAll();
    }

    @Override
    public void save(Type type) {
        iTypeRepo.save(type);
    }

    @Override
    public void update(Type type) {

    }

    @Override
    public Type findById(Long id) {
        return iTypeRepo.findById(id).orElseThrow(() -> new RuntimeException("Type not found"));
    }

    @Override
    public void remove(Long id) {
        iTypeRepo.deleteById(id);
    }
}
