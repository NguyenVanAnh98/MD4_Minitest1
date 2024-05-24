package com.example.md4_minitest_w2.controller;

import com.example.md4_minitest_w2.exception.ComputerCodeExists;
import com.example.md4_minitest_w2.exception.ComputerIdNotFound;
import com.example.md4_minitest_w2.exception.ResourceNotFound;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    @Autowired
    private IComputerService computerService;

    @Autowired
    private ITypeService typeService;

    @Value("${file-upload}")
    private String fileUpload;

    private static final Logger logger = Logger.getLogger(ComputerController.class.getName());

    @ModelAttribute("types")
    public Iterable<Type> listType() {
        return typeService.findAll();
    }

    @GetMapping
    public ModelAndView listComputer() {
        ModelAndView modelAndView = new ModelAndView("computer/list");
        Iterable<Computer> computers = computerService.findAll();
        modelAndView.addObject("computers", computers);
        logger.info("Listing all computers");
        return modelAndView;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("computer", new Computer());
        List<Type> types = (List<Type>) typeService.findAll();
        model.addAttribute("types", types);
        logger.info("Showing create computer form");
        return "computer/create";
    }

    @PostMapping("/create")
    public String createComputer(@Valid @ModelAttribute("computer") Computer computer, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation errors while creating computer: " + bindingResult.getAllErrors());
            return "computer/create";
        }

        computerService.existsByCode(computer.getCode());
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File(fileUpload + fileName));
                computer.setImage(fileName);
                logger.info("Uploaded file: " + fileName);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "File upload failed", e);
            }
        }

        computerService.save(computer);
        model.addAttribute("message", "Create new computer successfully");
        logger.info("Created new computer: " + computer);
        return "redirect:/computer";

    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) throws Exception {

        //---- Chưa dung exceptionHandler
//        try {
//            Computer computer = computerService.findById(id);
//            if (computer != null) {
//                ModelAndView modelAndView = new ModelAndView("computer/update");
//                modelAndView.addObject("computer", computer);
//                logger.info("Showing update form for computer ID: " + id);
//                return modelAndView;
//            } else {
//                logger.warning("Computer with ID " + id + " not found");
//                return new ModelAndView("404");
//            }
//        } catch (RuntimeException runtimeException) {
//            return new ModelAndView("404");
//        }

        Computer computer = computerService.findById(id);
        computerService.exitsById(id);
        if (computer != null) {
            ModelAndView modelAndView = new ModelAndView("computer/update");
            modelAndView.addObject("computer", computer);
            logger.info("Showing update form for computer ID: " + id);
            return modelAndView;
        } else {
            logger.warning("Computer with ID " + id + " not found");
            return new ModelAndView("error-IdNotFound");
        }


    }

    @PostMapping("/update/{id}")
    public String updateComputer(@Valid @ModelAttribute("computer") Computer computer, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            logger.warning("Validation errors while updating computer: " + bindingResult.getAllErrors());
            return "computer/update";
        }
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            try {
                file.transferTo(new File(fileUpload + fileName));
                computer.setImage(fileName);
                logger.info("Uploaded file: " + fileName);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "File upload failed", e);
            }
        }
        computerService.update(computer);
        model.addAttribute("message", "Update computer successfully");
        logger.info("Updated computer: " + computer);
        return "redirect:/computer";
    }

    @GetMapping("/delete/{id}")
    public String deleteComputer(@PathVariable Long id, RedirectAttributes redirect) throws Exception {
        Computer computer = computerService.findById(id);
        if (computer !=null) {
            computerService.remove(id);
            redirect.addFlashAttribute("message", "Delete computer successfully");
            logger.info("Deleted computer with ID: " + id);
        } else {
            redirect.addFlashAttribute("error", "Computer not found");
            logger.warning("Computer with ID " + id + " not found for deletion");
        }
        return "redirect:/computer";
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/404");
    }
    @ExceptionHandler(ComputerCodeExists.class)
    public ModelAndView computerNotFound() {
        return new ModelAndView("/error-exists-code");
    }
    @ExceptionHandler(ComputerIdNotFound.class)
    public ModelAndView computerIdNotFound() {
        return new ModelAndView("/error-IdNotFound");
    }


}
