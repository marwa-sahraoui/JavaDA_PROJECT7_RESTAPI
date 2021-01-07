package com.nnk.springboot.repository;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeRepositoryTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {
		Trade trade = new Trade("Trade Account", "Type");
        trade.setBuyQuantity(2.0);  //vue que buyquantity est mandatory on lui attribue une valeur
		// Save
		//on vérifie que l'identifiant de trade est non null
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getTradeId());
		Assert.assertTrue(trade.getAccount().equals("Trade Account"));

		// Update
		//on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		//on vérife que la taille de la liste >0
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		// on supprime un trade et on vérifie qu'il n'est présent en bDD
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
