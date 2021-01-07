package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepository;

    /**
     * permet de lister tous les ruleName existants dans la bdd
     * @return une liste des ruleName
     */
    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }

    /**
     * permet d'enregistrer un ruleName donné dans la bdd
     * @param ruleName l'objet à persister
     */
    public void save(RuleName ruleName){
        ruleNameRepository.save(ruleName);
    }

    /**
     * permet de chercher un ruleName à partir de son id
     * @param id l'identifiant de ruleName
     * @return ruleName qui posséde l'identifiant donné. Sinon une exception est levée
     */
    public RuleName findById(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    /**
     * permet la suppression d'un ruleName à partir de son id
     * @param id l'identifiant de ruleName
     */
    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
