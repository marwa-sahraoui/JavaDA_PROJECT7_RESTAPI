package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServicePasswordTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = new User(1, "marwa", "123", "marwa sahraoui", "user");
        userRepository.saveAndFlush(user);
    }

    @Test
    void findAllWillFindSize1AfterSavingUser() {
        //on enregistre un premier utilisateur et on vérifie que la taille d'utilisateur sera1
        List<User> users = userService.findAll();
        assertEquals(1, users.size());
        assertEquals("marwa", users.get(0).getUsername());
    }

    @Test
    void saveWillFailBecauseNoSpecialCharacterInPassword() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            //un mot de passe ne contenant pas un symbole va lever une exception
            userService.save(new User(2, "simpson", "hello2020", "Homer Simpson", "user"));
        });
    }

    @Test
    void saveWillFailBecauseNoNumberInPassword() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // un mot de passe ne contenant pas de chiffre va lever une exception
            userService.save(new User(2, "simpson", "Helloooooo@", "Homer Simpson", "user"));
        });
    }

    @Test
    void saveWillFailBecauseShortPassword() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // un mot de passe ayant une taille <8 va lever une exception
            userService.save(new User(2, "simpson", "Hello@2", "Homer Simpson", "user"));
        });
    }



    @AfterEach
    void cleanup(){
        this.userRepository.deleteAll();
    }
}