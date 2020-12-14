package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

    // TODO: Inject Trade service
    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model DONE
        List<Trade> trades = tradeService.findAll();
        model.addAttribute("tradez",trades);

        LOGGER.info("Loading page :trade/list + numbre of trades: " + trades.size());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {

        LOGGER.info("Loading page :bidList/add");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list DONE
        if(result.hasErrors()){
            LOGGER.error("Validation error on trade/add!!!");
            return "trade/add";
        }
        tradeService.save(trade);
        home(model);

        LOGGER.info("Loading page :trade/list + new bid adding + id: " + trade.getTradeId());
        return "trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form DONE

        Trade trade = tradeService.findById(id);
        model.addAttribute("trade",trade);
        LOGGER.info("Loading page :trade/update/id :updating trade with id: " + id);

        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list DONE
        if(result.hasErrors()) {
            return "redirect:/trade/update/" + id;
        }
        tradeService.save(trade);
        model.addAttribute("tradez",tradeService.findAll());
        LOGGER.info("Redirection to :trade/list with updating trade with id: " +trade.getTradeId());

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list

        tradeService.delete(id);

        LOGGER.info("Redirection to :trade/list with deleting trade with id: " + id );
        return "redirect:/trade/list";
    }
}
