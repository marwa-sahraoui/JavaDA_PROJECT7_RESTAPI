package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * permet de manipuler l'entité rating dans la BDD
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
