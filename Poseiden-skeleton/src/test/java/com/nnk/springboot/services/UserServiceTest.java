package com.nnk.springboot.services;
import com.nnk.springboot.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest


class UserServiceTest {

    @Test
    void encodePasswordUseBCrypt() {
        // instaciation de userService
        UserService userService = new UserService();
        
        // Utilisation de la méthode encodePassword pour un exemple
        String encodedPassword = userService.encodePassword("Hello@2021");

        // utilisation de l'algorithme BCrypt
        // comparaison de résultat de BCrypt avec le resultat de la methode encodePassword
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        assertTrue(bCryptPasswordEncoder.matches("Hello@2021", encodedPassword));
    }

    @Autowired
    private UserService userService;

    @Test
    public void userTest() {
        User user = new User();
        user.setFullname("Omar SY");
        user.setUsername("Omar");
        user.setRole("USER");
        user.setPassword("Hello@2021");

        // Save
        //on vérifie que l'identifiant de l'utilisateur est non null
        userService.save(user);
        user = userService.findById(user.getId());
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getFullname().equals("Omar SY"));

        // Update
        //on modifie un paramétre et on vérifie qu'il va remplacer l'ancien paramétre
        user.setFullname("Joe Bean");
        userService.save(user);
        user = userService.findById(user.getId());
        Assert.assertTrue(user.getFullname().equals("Joe Bean"));

        // Find
        //on vérife que la taille de la liste >0
        List<User> listResult = userService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        // on supprime l'utilisateur et on vérifie qu'il n'est présent en bDD
        Integer id = user.getId();
        userService.delete(id);
        assertThrows(IllegalArgumentException.class , () -> userService.findById(id));
    }
}
