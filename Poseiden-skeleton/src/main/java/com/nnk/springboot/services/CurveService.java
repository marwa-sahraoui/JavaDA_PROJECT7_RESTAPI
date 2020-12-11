package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll() {
       return curvePointRepository.findAll();
    }

     public void save(CurvePoint curvePoint) {

         curvePoint.setAsOfDate(Timestamp.valueOf(LocalDateTime.now()));
         curvePoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
         curvePointRepository.save(curvePoint);
     }


    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    }

    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
