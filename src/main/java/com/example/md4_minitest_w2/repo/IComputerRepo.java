package com.example.md4_minitest_w2.repo;

import com.example.md4_minitest_w2.model.Computer;
import com.example.md4_minitest_w2.model.Type;
import org.springframework.data.repository.CrudRepository;

public interface IComputerRepo extends CrudRepository<Computer, Long> {
    Iterable<Computer> findAllByTypeId(Type type);
}
