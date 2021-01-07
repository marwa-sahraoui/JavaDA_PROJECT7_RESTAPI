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

    // DONE: Inject RuleName service DONE
    @Autowired
    RuleNameService ruleNameService;

    /**
     * permet d'afficher la liste des ruleName
     * @param model
     * @return page ruleName/list.html
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // DONE: find all RuleName, add to model DONE
        List<RuleName> ruleNames = ruleNameService.findAll();
        model.addAttribute("ruleNamez",ruleNames);


        LOGGER.info("Loading page :ruleName/list + numbre of ruleNames: " + ruleNames.size());
        return "ruleName/list";
    }

    /**
     * permet d'afficher la page de formulaire d'ajout
     * @param ruleName
     * @return page ruleName/add.html
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        LOGGER.info("Loading page :ruleName/add");

        return "ruleName/add";
    }

    /**
     * permet de persister l'entité bid dans la BDD
     * @param ruleName
     * @param result
     * @param model
     * @return la page ruleName/list.html aprés l'enregistrement de l'entité ruleName.
     * Si pas de validation des champs contraints, on retourne au formulaire d'ajout des ruleName
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return RuleName list DONE
        if(result.hasErrors()){
            LOGGER.error("Validation error on ruleNAme/add!!!");

            return "ruleName/add";
        }
       ruleNameService.save(ruleName);
        home(model);

    LOGGER.info("Loading page :ruleName/list + new ruleName adding + id: " + ruleName.getId());
        return "ruleName/list";
    }

    /**
     * permet de faire la mise à jour en BDD
     * @param id
     * @param model
     * @return la page ruleName/update.html
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get RuleName by Id and to model then show to the form DONE

        RuleName ruleName = ruleNameService.findById(id);

        model.addAttribute("ruleName", ruleName);
        LOGGER.info("Loading page :ruleName/update/id :updating ruleNAme with id: " + id);

        return "ruleName/update";
    }

    /**
     * permet de valider la mise à jour en BDD
     * @param id
     * @param ruleName
     * @param result
     * @param model
     * @return la page ruleName/list.html aprés l'enregistrement de la mise à jour de l'entité ruleName.
     * Si pas de validation des champs contraints, on retourne au formulaire d'update des ruleName
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update RuleName and return RuleName list
        if(result.hasErrors()){
            return "redirect:/ruleName/update/" + id;
        }
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNamez", ruleNameService.findAll());

        LOGGER.info("Redirection to :ruleName/list with updating ruleName with id: " +ruleName.getId());
        return "redirect:/ruleName/list";
    }

    /**
     * permet la suppression d'un ruleName ayant un identifiant donné
     * @param id
     * @param model
     * @return la page ruleName/list.html après la suppresion du rating à identifiant donné
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // DONE: Find RuleName by Id and delete the RuleName, return to Rule list Done

        ruleNameService.delete(id);
        LOGGER.info("Redirection to :ruleName/list with deleting ruleNAme with id: " + id );
        return "redirect:/ruleName/list";
    }
}
