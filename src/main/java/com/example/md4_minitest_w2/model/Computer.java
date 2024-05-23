package com.example.md4_minitest_w2.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import javax.persistence.*;
import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Code không được để trống")
    @Pattern(regexp = "^CG.{6,}$", message = "Code phải bắt đầu bằng 'CG' và có tổng cộng 8 ký tự")
    private String code;
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    public Computer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}