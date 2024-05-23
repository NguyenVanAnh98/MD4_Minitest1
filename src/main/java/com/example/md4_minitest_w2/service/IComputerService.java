package com.example.md4_minitest_w2.service;

import com.example.md4_minitest_w2.model.Computer;
import com.example.md4_minitest_w2.model.Type;

public interface IComputerService extends IGenerateService<Computer> {
Iterable<Computer> findAllByType(Type type);
}
