package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTest {
    @Autowired
    private BidListService bidListService;

    @Test
    public void bidListTest() {

        BidList bid = new BidList();
        bid.setAccount("AccountTest");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        // Save
        //on vérifie que l'identifiant de bid est non null
        bidListService.save(bid);
        bid = bidListService.findById(bid.getBidListId());
        Assert.assertNotNull(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        //on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
        bid.setBidQuantity(20d);
        bidListService.save(bid);
        bid = bidListService.findById(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        //on vérife que la taille de la liste >0
        List<BidList> listResult = bidListService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        // on supprime un bid et on vérifie qu'il n'est présent en bDD
        Integer id = bid.getBidListId();
        bidListService.delete(id);
        assertThrows(IllegalArgumentException.class , () -> bidListService.findById(id));
    }
}
