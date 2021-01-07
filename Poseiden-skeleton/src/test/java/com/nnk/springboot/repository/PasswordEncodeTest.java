package com.nnk.springboot.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncodeTest {
    @Test
    // tester la méthode encode et avoir le resultat dans la console
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pw = encoder.encode("Marwa@2021");
        System.out.println("[ "+ pw + " ]");
    }
}
