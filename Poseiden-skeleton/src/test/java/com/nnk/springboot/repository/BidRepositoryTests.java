package com.nnk.springboot.repository;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidRepositoryTests {

	@Autowired
	private BidListRepository bidListRepository;

    //correction constructeur
	@Test
	public void bidListTest() {
		BidList bid = new BidList("Account Test", "Type Test", 10d);

		// Save
		//on vérifie que l'identifiant de bid est non null
		bid = bidListRepository.save(bid);
		Assert.assertNotNull(bid.getBidListId());
		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

		// Update
		//on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

		// Find
		//on vérife que la taille de la liste >0
		List<BidList> listResult = bidListRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		// on supprime un bid et on vérifie qu'il n'est présent en bDD
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		Assert.assertFalse(bidList.isPresent());
	}
}
