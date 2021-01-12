package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * permet de manipuler l'entité trade dans la BDD
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
