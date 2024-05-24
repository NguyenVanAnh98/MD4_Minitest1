package com.example.md4_minitest_w2.controller;

import com.example.md4_minitest_w2.model.Computer;
import com.example.md4_minitest_w2.model.Type;
import com.example.md4_minitest_w2.service.IComputerService;
import com.example.md4_minitest_w2.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private IComputerService computerService;

    @GetMapping
    public ModelAndView listTypes() {
        ModelAndView modelAndView = new ModelAndView("/type/list");
        Iterable<Type> types = typeService.findAll();
        modelAndView.addObject("type", types);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/type/create");
        modelAndView.addObject("type", new Type());
        return modelAndView;
    }

    @PostMapping("/create")
    public String createType(@ModelAttribute("type") Type type, RedirectAttributes redirectAttributes) {
        typeService.save(type);
        redirectAttributes.addFlashAttribute("message", "Created new type successfully");
        return "redirect:/type";
    }

    @GetMapping("/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id) throws Exception {
        Type typeOptional = typeService.findById(id);
        if (typeOptional != null) {
            ModelAndView modelAndView = new ModelAndView("/type/update");
            modelAndView.addObject("type", typeOptional);
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String updateType(@ModelAttribute("type") Type type, RedirectAttributes redirectAttributes) {
        typeService.save(type);
        redirectAttributes.addFlashAttribute("message", "Updated type successfully");
        return "redirect:/type";
    }

    @GetMapping("/view-type/{id}")
    public ModelAndView viewType(@PathVariable("id") Long id) throws Exception {
        Type typeOptional = typeService.findById(id);
        if (typeOptional != null) {
            return new ModelAndView("/error_404");
        }

        Iterable<Computer> computers = computerService.findAllByType(typeOptional);

        ModelAndView modelAndView = new ModelAndView("/computer/list");
        modelAndView.addObject("computers", computers);
        return modelAndView;
    }
}
