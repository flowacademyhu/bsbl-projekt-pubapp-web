package org.flow.controllers;


import org.flow.models.User;
import org.flow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



//http://localhost:8080/users/registration?password=1234&firstName=istvan&lastName=nagy&nickName=tyson&email=jesus@email.com&dob=1900-01-01&gender=true
@RestController
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping(path="/")
    public String getAllUsers (Model model) {
        model.addAttribute("user", userRepository.findAll());
        return "userList";
    }

    //get user by ID
    @GetMapping(path = "/{id}")
    public String getUserById (@PathVariable("id") long id, Model model)  throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found.");
        }

        model.addAttribute("user", userRepository.findById(id).get());
        return "user";
    }


    //get create user form
    @GetMapping(path="/registration")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "userNew";
    }


    //create new user
    @PostMapping(path="/registration")
    public @ResponseBody String addNewUser (@ModelAttribute User user, Model model) {
        User newUser = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setNickName(user.getNickName());
        newUser.setEmail(user.getEmail());
        newUser.setDob(user.getDob());
        newUser.setGender(user.getGender());
        userRepository.save(newUser);
        model.addAttribute("user", userRepository.findAll());
        return "userList";
    }


    //get user edit form
    @GetMapping(path="/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "userEdit";
    }

    //update user
    @PostMapping(path="/{id}/edit")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute User user,
                             Model model) {
        User updatedUser = userRepository.findById(id).get();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        updatedUser.setPassword(hashedPassword);
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setNickName(user.getNickName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setDob(user.getDob());
        updatedUser.setGender(user.getGender());
        model.addAttribute("user", userRepository.findAll());
        return "userList";
    }



    //delete user by ID
    @PostMapping(path = "/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userRepository.deleteById(id);
        model.addAttribute("user", userRepository.findAll());
        return "userList";
    }

}
