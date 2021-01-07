package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurveService {

    @Autowired
    CurvePointRepository curvePointRepository;

    public List<CurvePoint> findAll() {
       return curvePointRepository.findAll();
    }


}
