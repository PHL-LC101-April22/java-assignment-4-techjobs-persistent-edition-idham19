package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("employers")
public class EmployerController {
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private JobRepository jobRepository;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("title","All employers");
        model.addAttribute("employers",employerRepository.findAll());
        return "employers/index";
    }

//    @GetMapping
//    public String displayAllEmployer(@RequestParam(required = false) Integer employerId, Model model) {
//        if (employerId == null) {
//            model.addAttribute("title", "All Employers");
//            model.addAttribute("employers", employerRepository.findAll());
//        } else {
//
//            Optional<Job> result = jobRepository.findById(employerId);
//            if (result.isEmpty()) {
//                model.addAttribute("title", "invalid employer id " + employerId);
//            } else {
//
//
//                Job job = result.get();
//                model.addAttribute("title", "employer Name is :" + job.getName());
//                model.addAttribute("jobs", job.getEmployer());
//            }
//        }
//        return "employers/index";
//    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute("title","add Employer");
        model.addAttribute(new Employer());
        model.addAttribute("employers",employerRepository.findAll());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer employer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title","add employer");
            return "employers/add";
        }
        employerRepository.save(employer);
        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}
