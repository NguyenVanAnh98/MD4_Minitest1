package com.example.md4_minitest_w2.model;

import org.springframework.web.multipart.MultipartFile;

public class ComputerForm {
    private Long id;
    private String code;
    private String name;
    private String type;
    private MultipartFile urlImage;

    public ComputerForm(Long id, String code, String name, String type, MultipartFile urlImage) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.urlImage = urlImage;
    }

    public ComputerForm() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(MultipartFile urlImage) {
        this.urlImage = urlImage;
    }
}
