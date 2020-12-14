package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
    // TODO: Inject Rating service DONE
    @Autowired
    RatingService ratingService;
    @Autowired
    RatingRepository ratingRepository;

    @RequestMapping("/rating/list")

    public String home(Model model)
    {
        // TODO: find all Rating, add to model Done
        List<Rating> ratings = ratingService.findAll();
        model.addAttribute("ratingz",ratings);

        LOGGER.info("Loading page :rating/list + numbre of rating: " + ratings.size());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        LOGGER.info("Loading page :rating/add");

        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list DONE

       if(result.hasErrors()) {

           LOGGER.error("Validation error on rating/add!!!");
           return "rating/add";
       }

      ratingService.save(rating);
        home(model);


        LOGGER.info("Loading page :rating/list + new rating adding + id: " + rating.getId());
        return "rating/list";

    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form DONE

        Rating rating = ratingService.findById(id);

        model.addAttribute("rating", rating);

        LOGGER.info("Loading page :rating/update/id :updating rating with id: " + id);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list DONE
        if(result.hasErrors()){
            return "redirect:/rating/update/" + id;
        }
        ratingService.save(rating);
        model.addAttribute("ratingz", ratingService.findAll());

        LOGGER.info("Redirection to :rating/list with updating rating with id: " +rating.getId());

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list DONE

       ratingService.delete(id);
        LOGGER.info("Redirection to :rating/list with deleting rating with id: " + id );
        return "redirect:/rating/list";
    }
}
