package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Test
    public void tradeTest() {
        Trade trade = new Trade("Trade Account", "Type");
        trade.setBuyQuantity(2.0);  //vue que buyquantity est mandatory on lui attribue une valeur
        // Save
        //on vérifie que l'identifiant de trade est non null
         tradeService.save(trade);
        Assert.assertNotNull(trade.getTradeId());
        Assert.assertTrue(trade.getAccount().equals("Trade Account"));

        // Update
        //on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
        trade.setAccount("Trade Account Update");
        tradeService.save(trade);
        Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

        // Find
        //on vérife que la taille de la liste >0
        List<Trade> listResult = tradeService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        // on supprime le trade et on vérifie qu'il n'est présent en bDD
        Integer id = trade.getTradeId();
        tradeService.delete(id);
        assertThrows(IllegalArgumentException.class , () -> tradeService.findById(id));
    }
}
