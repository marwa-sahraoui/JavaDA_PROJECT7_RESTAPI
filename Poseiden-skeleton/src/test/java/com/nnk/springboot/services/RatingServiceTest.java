package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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

public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;

    @Test
    public void ratingTest() {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        //on vérifie que l'identifiant de rating est non null
        ratingService.save(rating);
        rating = ratingService.findById(rating.getId());
        Assert.assertNotNull(rating.getId());
        Assert.assertTrue(rating.getOrderNumber() == 10);



        // Update
        //on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
        rating.setOrderNumber(20);
        ratingService.save(rating);
        rating = ratingService.findById(rating.getId());
        Assert.assertEquals(20, rating.getOrderNumber().intValue());

        // Find
        //on vérife que la taille de la liste >0
        List<Rating> listResult = ratingService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        // on supprime un rating et on vérifie qu'il n'est présent en bDD
        Integer id = rating.getId();
        ratingService.delete(id);
        assertThrows(IllegalArgumentException.class , () -> ratingService.findById(id));
    }
}
