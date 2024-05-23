package com.example.md4_minitest_w2.controller;

import com.example.md4_minitest_w2.model.Computer;
import com.example.md4_minitest_w2.model.Type;
import com.example.md4_minitest_w2.service.IComputerService;
import com.example.md4_minitest_w2.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/computers")
public class ComputerController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ITypeService typeService;

    @ModelAttribute("types")
    public Iterable<Type> listProvinces() {
        return typeService.findAll();
    }

    @GetMapping
    public ModelAndView listCustomer() {
        Iterable<Computer> customers = computerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/computer/list");
        modelAndView.addObject("computers", customers);
        return modelAndView;
    }

}
