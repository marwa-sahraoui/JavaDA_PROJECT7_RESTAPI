package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * permet de manipuler l'entité curvePoint dans la BDD
 */
@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

  List<CurvePoint> findAll();

  void findAllById(Integer id);
}
