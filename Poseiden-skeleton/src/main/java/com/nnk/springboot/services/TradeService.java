package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;

    /**
     * permet de lister tous les trades existants dans la bdd
     * @return une liste des trades
     */
    public List<Trade> findAll() {
       return tradeRepository.findAll();
    }

    /**
     * permet d'enregistrer un trade donné dans la bdd
     * @param trade l'objet à persister
     */
    public void save(Trade trade) {

        trade.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        trade.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
        tradeRepository.save(trade);
    }

    /**
     * permet de chercher un trade à partir de son id
     * @param id l'identifiant de trade
     * @return trade qui posséde l'identifiant donné. Sinon une exception est levée
     */
    public Trade findById(Integer id) {
      return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    /**
     * permet la suppression d'un trade à partir de son id
     * @param id l'identifiant de trade
     */
    public void delete(Integer id) {
        tradeRepository.deleteById(id);
    }
}
