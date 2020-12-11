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
    @Autowired
    BidListRepository bidListRepository;

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public void save(BidList bid) {
        bid.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        bid.setRevisionDate(Timestamp.valueOf(LocalDateTime.now()));
        bidListRepository.save(bid);

    }

    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bidList Id:" + id));
    }

    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }
}
