package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * classe service permettant de manipuler les entités curvePoints
 */
@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    /**
     * permet de lister tous les curvePoints existants dans la bdd
     * @return une liste des curvePoints
     */
    public List<CurvePoint> findAll() {
       return curvePointRepository.findAll();
    }

    /**
     * permet d'enregistrer un curvePoint donné dans la bdd
     * @param curvePoint l'objet à persister
     */
     public void save(CurvePoint curvePoint) {
         curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
         curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
         curvePointRepository.save(curvePoint);
     }

    /**
     * permet de chercher un curvePoint à partir de son id
     * @param id l'identifiant de curvePoint
     * @return curvePoint qui posséde l'identifiant donné. Sinon une exception est levée
     */
    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    /**
     * permet la suppression d'un curvePoint à partir de son id
     * @param id l'identifiant de curvePoint
     */
    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
