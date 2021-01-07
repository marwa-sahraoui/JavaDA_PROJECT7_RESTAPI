package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    /**
     * permet de lister tous les rating existants dans la bdd
     * @return une liste des rating
     */
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * permet de chercher un trade à partir de son id
     * @param id l'identifiant de trade
     * @return trade qui posséde l'identifiant donné. Sinon une exception est levée
     */
    public Rating findById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    }

    /**
     * permet d'enregistrer un rating donné dans la bdd
     * @param rating l'objet à persister
     */
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * permet la suppression d'un rating à partir de son id
     * @param id l'identifiant de rating
     */
    public void delete(Integer id) {
        ratingRepository.deleteById(id);
    }
}