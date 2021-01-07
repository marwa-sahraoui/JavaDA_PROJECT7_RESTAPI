package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidListService {
    //injection de bidListRepository
    @Autowired
    BidListRepository bidListRepository;
    //test unit
    public BidListService(BidListRepository bidListRepository) {
    }

    /**
     * permet de lister tous les bidList existants dans la bdd
     * @return une liste des bidList
     */
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * permet d'enregistrer un bidList donné dans la bdd
     * @param bid l'objet à persister
     */
    public void save(BidList bid) {
        bid.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        bid.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
        bidListRepository.save(bid);

    }

    /**
     * permet de chercher un bidList à partir de son id
     * @param id l'identifiant de bidList
     * @return bidList qui posséde l'identifiant donné. Sinon une exception est levée
     */
    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    /**
     * permet la suppression d'un bidList à partir de son id
     * @param id l'identifiant de bidList
     */
    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }
}
