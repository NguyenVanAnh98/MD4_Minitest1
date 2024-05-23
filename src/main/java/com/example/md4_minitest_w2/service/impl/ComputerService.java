package com.example.md4_minitest_w2.service.impl;

import com.example.md4_minitest_w2.model.Computer;
import com.example.md4_minitest_w2.model.Type;
import com.example.md4_minitest_w2.repo.IComputerRepo;
import com.example.md4_minitest_w2.service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class ComputerService implements IComputerService {
    @Autowired
    private IComputerRepo iComputerRepo;

    @Override
    public Iterable<Computer> findAll() {
        return iComputerRepo.findAll();
    }

    @Override
    public void save(Computer computer) {
        iComputerRepo.save(computer);
    }

    @Override
    public Optional<Computer> findById(Long id) {
        return iComputerRepo.findById(id);
    }

    @Override
    public void remove(Long id) {
        iComputerRepo.deleteById(id);
    }

    @Override
    public Iterable<Computer> findAllByType(Type type) {
        return iComputerRepo.findAllByTypeId(type);
    }
}