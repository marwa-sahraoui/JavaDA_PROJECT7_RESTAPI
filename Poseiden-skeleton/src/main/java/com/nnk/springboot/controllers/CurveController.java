package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class CurveController {
    // TODO: Inject Curve Point service
    @Autowired
    CurveService curveService;

    @Autowired
    CurvePointRepository curvePointRepository;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // TODO: find all Curve Point, add to model :DONE
        List<CurvePoint> curvePoints = curveService.findAll();

        model.addAttribute("curvePointaz", curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list:DONE
        /*
        bindingresult enregistre les erreurs pour appliquer le validator
         si on a des erreurs, on les collecte dans un set
         à partir d'un stream
         */
//        if (result.hasErrors()) {
//
//            Set<String> fields = result.getFieldErrors()
//
//                    .stream()
//                    .map(fieldError -> fieldError.getField())
//                    .collect(Collectors.toSet());
//            /*
//            si le set contient asofdate ou creationdate on va les donner le temps actuel localdateTime.now
//
//             */
//
//            if (fields.contains("asOfDate") && fields.contains("creationDate") && fields.size() == 2) {
//                curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
//                curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
//            } else {
//                return "redirect:/curvePoint/add";
//            }
//        }
       curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
       curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now())); //en service
        curvePointRepository.save(curvePoint);

        home(model);

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form ...

        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));

        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list ..

        if (result.hasErrors()) {

            Set<String> fields = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField())
                    .collect(Collectors.toSet());
            /*
            mm principe mais ce qui va changer si on fait une mise a jour creationdate c la mm que dans la methode
            add mais ce qui va changer c'est l'attribut asofdate
             */
            if (fields.contains("asOfDate") && fields.contains("creationDate") && fields.size() == 2) {
                CurvePoint savedCurvePoint = curvePointRepository.getOne(id);
                curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
                curvePoint.setCreationDate(savedCurvePoint.getCreationDate());
            } else {
                return "redirect:/curvePoint/update/" + id;
            }
        }

        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePointaz", curvePointRepository.findAll());

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list..

        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
        curvePointRepository.delete(curvePoint);
        model.addAttribute("curvePointz", curvePointRepository.findAll());
        return "redirect:/curvePoint/list";
    }
}
