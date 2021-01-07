package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CurveServiceTest {
    @Autowired
    private CurveService curveService;

    @Test
    public void curvePointTest() {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);

        //correction de constructeur +ajout des attribut mandatory
        curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
        curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));


        // Save
        //on vérifie que l'identifiant de curve est non null
       curveService.save(curvePoint);
        curvePoint = curveService.findById(curvePoint.getId());
        Assert.assertNotNull(curvePoint.getId());

        Assert.assertTrue(curvePoint.getCurveId() == 10);

        // Update
        //on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
        curvePoint.setCurveId(20);
        curveService.save(curvePoint);
        curvePoint = curveService.findById(curvePoint.getId());
        Assert.assertEquals(20, curvePoint.getCurveId().intValue());

        // Find
        //on vérife que la taille de la liste >0
        List<CurvePoint> listResult = curveService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        // on supprime un curve et on vérifie qu'il n'est présent en bDD
        Integer id = curvePoint.getId();
        curveService.delete(id);
        assertThrows(IllegalArgumentException.class , () -> curveService.findById(id));
    }


}
