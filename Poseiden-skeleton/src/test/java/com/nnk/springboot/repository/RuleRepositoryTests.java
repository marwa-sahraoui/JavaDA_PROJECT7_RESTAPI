package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleRepositoryTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;
    //correction de constructeur
	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		//on vérifie que l'identifiant de rule est non null
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		//on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		//on vérife que la taille de la liste >0
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		// on supprime un rule et on vérifie qu'il n'est présent en bDD
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
