package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RuleNameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);

    // TODO: Inject RuleName service DONE
    @Autowired
    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model DONE
        List<RuleName> ruleNames = ruleNameService.findAll();
        model.addAttribute("ruleNamez",ruleNames);


        LOGGER.info("Loading page :ruleName/list + numbre of ruleNames: " + ruleNames.size());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        LOGGER.info("Loading page :ruleName/add");

        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list DONE
        if(result.hasErrors()){
            LOGGER.error("Validation error on ruleNAme/add!!!");

            return "ruleName/add";
        }
       ruleNameService.save(ruleName);
        home(model);

    LOGGER.info("Loading page :ruleName/list + new ruleName adding + id: " + ruleName.getId());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form DONE

        RuleName ruleName = ruleNameService.findById(id);

        model.addAttribute("ruleName", ruleName);
        LOGGER.info("Loading page :ruleName/update/id :updating ruleNAme with id: " + id);

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if(result.hasErrors()){
            return "redirect:/ruleName/update/" + id;
        }
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNamez", ruleNameService.findAll());

        LOGGER.info("Redirection to :ruleName/list with updating ruleName with id: " +ruleName.getId());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list Done

        ruleNameService.delete(id);
        LOGGER.info("Redirection to :ruleName/list with deleting ruleNAme with id: " + id );
        return "redirect:/ruleName/list";
    }
}
