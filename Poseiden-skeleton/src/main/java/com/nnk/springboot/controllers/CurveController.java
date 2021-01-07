package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class CurveController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);

    // DONE: Inject Curve Point service
    @Autowired
    CurveService curveService;

    @Autowired
    CurvePointRepository curvePointRepository;

    /**
     * permet d'afficher la liste des curvePoint
     * @param model
     * @return page curvePoint/list.html
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // DONE: find all Curve Point, add to model :DONE
        List<CurvePoint> curvePoints = curveService.findAll();

        model.addAttribute("curvePointaz", curvePoints);
        LOGGER.info("Loading page :curvePoint/list + numbre of curvePoints: " + curvePoints.size());

        return "curvePoint/list";
    }

    /**
     * permet d'afficher la page de formulaire d'ajout
     * @param curvePoint
     * @return page curvePoint/add.html
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {

        LOGGER.info("Loading page :curvePoint/add");
        return "curvePoint/add";
    }

    /**
     * permet de persister l'entité bid dans la BDD
     * @param curvePoint
     * @param result
     * @param model
     * @return la page curvePoint/list.html aprés l'enregistrement de l'entité curvePoint.
     * Si pas de validation des champs contraints, on retourne au formulaire d'ajout des curvePoint
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return Curve list:DONE
        if (result.hasErrors()) {
            LOGGER.error("Validation error on curvePoint/add!!!");

            return "curvePoint/add";
        }

        curveService.save(curvePoint);
        home(model);

        LOGGER.info("Loading page :curvePoint/list + new curvePoint adding + id: " + curvePoint.getCurveId());
        return "curvePoint/list";
    }

    /**
     * permet de faire la mise à jour en BDD
     * @param id
     * @param model
     * @return la page curvePoint/update.html
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get CurvePoint by Id and to model then show to the form ...

        CurvePoint curvePoint = curveService.findById(id);

        model.addAttribute("curvePoint", curvePoint);

        LOGGER.info("Loading page :curvePoint/update/id :updating curvePoint with id: " + id);
        return "curvePoint/update";
    }

    /**
     * permet de valider la mise à jour en BDD
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @return la page curvePoint/list.html aprés l'enregistrement de la mise à jour de l'entité curvePoint.
     * Si pas de validation des champs contraints, on retourne au formulaire d'update des curvePoint
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Curve and return Curve list ..

        if (result.hasErrors()) {

            return "redirect:/curvePoint/update/" + id;
        }

        curveService.save(curvePoint);
        model.addAttribute("curvePointaz", curvePointRepository.findAll());

        LOGGER.info("Redirection to :curvePoint/list with updating curvePoint with id: " +curvePoint.getCurveId());
        return "redirect:/curvePoint/list";

    }

    /**
     * permet la suppression d'un curvePoint ayant un identifiant donné
     * @param id
     * @param model
     * @return la page curvePoint/list.html après la suppresion du curvePoint à identifiant donné
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Curve by Id and delete the Curve, return to Curve list..

        curveService.delete(id);
        LOGGER.info("Redirection to :curvePoint/list with deleting curvePoint with id: " + id );
        return "redirect:/curvePoint/list";
    }
}
