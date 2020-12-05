package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepository;

    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }
    public void save(RuleName ruleName){
        ruleNameRepository.save(ruleName);
    }

    public RuleName findById(Integer id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
