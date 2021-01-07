package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);

    // Done: Inject Bid service
     @Autowired
    BidListService bidListService;

    /**
     * permet d'afficher la liste des bidList
     * @param model
     * @return page bidList/list.html
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // DONE: call service find all bids to show to the view
        List<BidList> bidlists = bidListService.findAll();
        model.addAttribute("bidlistz" ,bidlists);
       LOGGER.info("Loading page :bidList/list + numbre of bidlists: " + bidlists.size());
        return "bidList/list";

    }

    /**
     * permet d'afficher la page de formulaire d'ajout
     * @param bid
     * @return page bidList/add.html
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        LOGGER.info("Loading page :bidList/add");

        return "bidList/add";
    }

    /**
     * permet de persister l'entité bid dans la BDD
     * @param bid
     * @param result
     * @param model
     * @return la page bidList/list.html aprés l'enregistrement de l'entité bid.
     * Si pas de validation des champs contraints, on retourne au formulaire d'ajout des bidList
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return bid list DONE
        if(result.hasErrors()){
            LOGGER.error("Validation error on bidList/add!!!");

            return "bidList/add";
        }

        bidListService.save(bid);
        home(model);

        LOGGER.info("Loading page :bidList/list + new bid adding + id: " + bid.getBidListId());
        return "bidList/list";
    }

    /**
     * permet de faire la mise à jour en BDD
     * @param id
     * @param model
     * @return la page bidList/update.html
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Bid by Id and to model then show to the form DONE

        BidList bidList = bidListService.findById(id);
        model.addAttribute("bidListz",bidList);
        LOGGER.info("Loading page :bidList/update/id :updating bidList with id: " + id);

        return "bidList/update";
    }

    /**
     * permet de valider la mise à jour en BDD
     * @param id
     * @param bidList
     * @param result
     * @param model
     * @return la page bidList/list.html aprés l'enregistrement de la mise à jour de l'entité bid.
     * Si pas de validation des champs contraints, on retourne au formulaire d'update des bidList
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Bid and return list Bid DONE
        if(result.hasErrors()){
            return "redirect:/bidList/update/" + id;
        }

        bidListService.save(bidList);
        model.addAttribute("bidListz", bidListService.findAll());
        LOGGER.info("Redirection to :bidList/list with updating bidList with id: " +bidList.getBidListId());

        return "redirect:/bidList/list";
    }

    /**
     * permet la suppression d'un bid ayant un identifiant donné
     * @param id
     * @param model
     * @return la page bidList/list.html après la suppresion du bidList à identifiant donné
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Bid by Id and delete the bid, return to Bid list Done

        bidListService.delete(id);
        LOGGER.info("Redirection to :bidList/list with deleting bidList with id: " + id );
        return "redirect:/bidList/list";
    }
}
