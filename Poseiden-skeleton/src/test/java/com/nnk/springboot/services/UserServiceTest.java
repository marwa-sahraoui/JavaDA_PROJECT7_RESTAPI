package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeAll
    void setUp() {
        User user = new User(1, "marwa", "123", "marwa sahraoui", "user");
        userRepository.save(user);
    }

    @Test
    void findAllWillFindSize1AfterSavingUser() {
        List<User> users = userService.findAll();
        assertEquals(1, users.size());
        assertEquals("marwa", users.get(0).getUsername());
    }

    @Test
    void saveWillFindUserRepoAccountAdding1ToTheInitialNumber() {
        long initial = userRepository.count();
        userService.save(new User(2, "simpson", "Hello@2020", "Homer Simpson", "user"));
        assertEquals(initial + 1, userRepository.count());

        userRepository.deleteById(2);
    }

    @Test
    void saveWillFailBecauseNoSpecialCharacterInPassword() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.save(new User(2, "simpson", "hello2020", "Homer Simpson", "user"));
        });
    }

    @Test
    void saveWillFailBecauseNoNumberInPassword() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.save(new User(2, "simpson", "Helloooooo@", "Homer Simpson", "user"));
        });
    }

    @Test
    void saveWillFailBecauseShortPassword() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.save(new User(2, "simpson", "Hello@2", "Homer Simpson", "user"));
        });
    }

    @Test
    void findById() {
        User user = userService.findById(1);
        assertEquals("marwa", user.getUsername());
    }

    @Test
    void deleteById() {
        long initial = userRepository.count();

        userService.deleteById(1);

        List<User> users = userService.findAll();
        assertEquals(initial - 1, users.size());
    }
}