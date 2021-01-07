package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private static final String SPECIAL_CHARACTER_FORMAT = "(?=.*[@#$%^&+=]).+";
    private static final String NUMBER_CHARACTER_FORMAT = "(?=.*[0-9]).+";
    private static final String CAPS_CHARACTER_FORMAT = "(?=.*[A-Z]).+";
    private static final String SMALL_CAPS_CHARACTER_FORMAT = "(?=.*[a-z]).+";

    @Autowired
    UserRepository userRepository;
    
    /**
     * permet de lister tous les utilisateurs existants dans la bdd
     * @return une liste des trades
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * permet d'enregistrer un user donné dans la bdd
     * @param user l'objet à persister
     */
    public void save(User user) {
        String password = user.getPassword();

        // regles de gestions de MDP en utilisant regex
        if (password.length() < 8) {
            LOGGER.error("mot de passe trop court!!" );
            throw new IllegalStateException("mot de passe trop court!!");
        }

        if (!Pattern.compile(SPECIAL_CHARACTER_FORMAT).matcher(password).matches()) {
            LOGGER.error("mot de passe ne contient pas de character specifique!!");
            throw new IllegalStateException("mot de passe ne contient pas de character specifique!!");
        }

        if (!Pattern.compile(NUMBER_CHARACTER_FORMAT).matcher(password).matches()) {
            LOGGER.error("mot de passe ne contient pas un numéro!!");
            throw new IllegalStateException("mot de passe ne contient pas un numéro!!");
        }

        if (!Pattern.compile(CAPS_CHARACTER_FORMAT).matcher(password).matches()) {
            LOGGER.error("mot de passe ne contient pas une lettre majuscule!!");
            throw new IllegalStateException("mot de passe ne contient pas une lettre majuscule!!");
        }

        if (!Pattern.compile(SMALL_CAPS_CHARACTER_FORMAT).matcher(password).matches()) {
            LOGGER.error("mot de passe ne contient pas une lettre minuscule!!");
            throw new IllegalStateException("mot de passe ne contient pas une lettre minuscule!!");
        }
        //une fois MDP répond aux contraintes on va l'encoder
        user.setPassword(encodePassword(password));

        userRepository.save(user);
    }

    /**
     * permet de chercher un utilisateur à partir de son id
     * @param id l'identifiant de l'utilisateur
     * @return un utilisateur qui posséde l'identifiant donné. Sinon une exception est levée
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * permet de crypter le mot de passe
     * @param userPassword mot de passe en clair
     * @return password encodé
     */
    public String encodePassword(String userPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(userPassword);
    }

    /**
     * permet la suppression d'un utilisateur à partir de son id
     * @param id l'identifiant de l'utilisateur
     */
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }


    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
