package com.example.md4_minitest_w2.service.impl;

import com.example.md4_minitest_w2.exception.ComputerCodeExists;
import com.example.md4_minitest_w2.exception.ComputerIdNotFound;
import com.example.md4_minitest_w2.exception.ResourceNotFound;
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
    public void update(Computer computer) {
        Computer computerUpdated = iComputerRepo.findById(computer.getId()).orElseThrow(() -> new RuntimeException("Computer not found"));


        computerUpdated.setName(computer.getName());
        computerUpdated.setCode(computer.getCode());
        computerUpdated.setImage(computer.getImage());
        computerUpdated.setType(computer.getType());
        iComputerRepo.save(computerUpdated);
    }

    @Override
    public Computer findById(Long id) {
        return iComputerRepo.findById(id).orElseThrow(() -> new ComputerIdNotFound("Computer not found"));
    }
//    @Override
//    public Computer findById(Long id) {
//        return iComputerRepo.findById(id).orElseThrow(() -> new RuntimeException("Computer not found"));
//    }

    @Override
    public void remove(Long id) {
        iComputerRepo.deleteById(id);
    }

    @Override
    public Iterable<Computer> findAllByType(Type type) {
        return iComputerRepo.findAllByTypeId(type);
    }

    @Override
    public Boolean existsByCode(String code) {
        Boolean exist = iComputerRepo.existsByCode(code);
        if (exist) {
            throw new ComputerCodeExists("Computer with code " + code + " not found");
        }
        return iComputerRepo.existsByCode(code);
    }

    @Override
    public Boolean exitsById(Long id) {
        Boolean exist = iComputerRepo.existsById(id);
        if (!exist) {
            throw new ComputerIdNotFound("Computer with id " + id + " not found");
        }
        return iComputerRepo.existsById(id);
    }
}