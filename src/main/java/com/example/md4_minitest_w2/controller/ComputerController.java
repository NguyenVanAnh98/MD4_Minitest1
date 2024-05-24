package com.example.md4_minitest_w2.controller;

import com.example.md4_minitest_w2.model.Computer;
import com.example.md4_minitest_w2.model.Type;
import com.example.md4_minitest_w2.service.IComputerService;
import com.example.md4_minitest_w2.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ITypeService typeService;

    @Value("${file-upload}")
    private String fileUpload;

    @ModelAttribute("types")
    public Iterable<Type> listType() {
        return typeService.findAll();
    }

    @GetMapping
    public ModelAndView listComputer() {
        ModelAndView modelAndView = new ModelAndView("list");
        Iterable<Computer> computers = computerService.findAll();
        modelAndView.addObject("computers", computers);
        return modelAndView;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("computer", new Computer());
        List<Type> types = (List<Type>) typeService.findAll();
        model.addAttribute("types", types);
        return "create";
    }

    @PostMapping("/create")
    public String createComputer(@Valid @ModelAttribute("computer") Computer computer, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File(fileUpload + fileName));
                computer.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        computerService.save(computer);
        model.addAttribute("message", "Create new computer successfully");
        return "redirect:/computer";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Computer> computer = computerService.findById(id);
        if (computer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("update");
            modelAndView.addObject("computer", computer.get());
            return modelAndView;
        } else {
            return new ModelAndView("error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String updateComputer(@Valid @ModelAttribute("computer") Computer computer, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            return "update";
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File(fileUpload + fileName));
                computer.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        computerService.save(computer);
        model.addAttribute("message", "Update computer successfully");
        return "redirect:/computer";
    }

    @GetMapping("/delete/{id}")
    public String deleteComputer(@PathVariable Long id, RedirectAttributes redirect) {
        Optional<Computer> computer = computerService.findById(id);
        if (computer.isPresent()) {
            computerService.remove(id);
            redirect.addFlashAttribute("message", "Delete computer successfully");
        } else {
            redirect.addFlashAttribute("error", "Computer not found");
        }
        return "redirect:/computer";
    }
}
