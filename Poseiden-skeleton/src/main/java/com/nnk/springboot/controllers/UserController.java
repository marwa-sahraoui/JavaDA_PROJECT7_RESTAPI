package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        List<User> users = userService.findAll();

        model.addAttribute("users", users);

        LOGGER.info("Loading page :user/list + numbre of users: " + users.size());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {

        LOGGER.info("Loading page :user/add");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {

            try {
                userService.save(user);
            } catch (Exception e) {
               model.addAttribute("messageerror", e.getMessage());
                return "user/add";
            }

            LOGGER.info("Loading page :user/list + new user adding + id: " + user.getId());
            return "user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id);
        // on masque le password actuel pour des raisons de securité
        user.setPassword("");
        model.addAttribute("userz",user);
        LOGGER.info("Loading page :user/update/id :updating user with id: " + id);

        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            LOGGER.error("Validation error on user/update/id with id: " + id);
            return "user/update";
        }

        user.setPassword(userService.encodePassword(user.getPassword()));
        user.setId(id);
        userService.save(user);
        model.addAttribute("users", userService.findAll());

        LOGGER.info("Redirection to :user/list with updating user with id: " +user.getId());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        userService.deleteById(id);
        LOGGER.info("Redirection to :user/list with deleting user with id: " + id );

        return "redirect:/user/list";
    }
}
