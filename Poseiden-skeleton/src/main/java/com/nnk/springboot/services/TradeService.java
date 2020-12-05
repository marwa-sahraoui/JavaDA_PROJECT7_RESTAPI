package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;

    public List<Trade> findAll() {
       return tradeRepository.findAll();
    }

    public void save(Trade trade) {
        tradeRepository.save(trade);
    }

    public Trade findById(Integer id) {
      return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    public void delete(Integer id) {
        tradeRepository.deleteById(id);
    }
}
