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

    // TODO: Inject Bid service Done
     @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view Done
        List<BidList> bidlists = bidListService.findAll();
        model.addAttribute("bidlistz" ,bidlists);
        //log
       LOGGER.info("Loading page :bidList/list + numbre of bidlists: " + bidlists.size());
        return "bidList/list";

    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        LOGGER.info("Loading page :bidList/add");

        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list DONE
        if(result.hasErrors()){
            LOGGER.error("Validation error on bidList/add!!!");

            return "bidList/add";
        }

        bidListService.save(bid);
        home(model);

        LOGGER.info("Loading page :bidList/list + new bid adding + id: " + bid.getBidListId());
        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form DONE

        BidList bidList = bidListService.findById(id);
        model.addAttribute("bidListz",bidList);
        LOGGER.info("Loading page :bidList/update/id :updating bidList with id: " + id);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid DONE
        if(result.hasErrors()){
            return "redirect:/bidList/update/" + id;
        }

        bidListService.save(bidList);
        model.addAttribute("bidListz", bidListService.findAll());
        LOGGER.info("Redirection to :bidList/list with updating bidList with id: " +bidList.getBidListId());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list Done

        bidListService.delete(id);
        LOGGER.info("Redirection to :bidList/list with deleting bidList with id: " + id );
        return "redirect:/bidList/list";
    }
}
