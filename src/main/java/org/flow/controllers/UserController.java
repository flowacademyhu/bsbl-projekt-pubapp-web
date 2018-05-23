package org.flow.controllers;


import org.flow.models.User;
import org.flow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping(path="/")
    public @ResponseBody Iterable<User> getAllUsers () {
        return userRepository.findAll();
    }

    //get user by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody User getUserById (@PathVariable("id") Long id)  throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found.");
        }
        return user.get();
    }

    //create new user
    @PostMapping(path="/")
    public @ResponseBody User addNewUser (@RequestParam String firstName, @RequestParam String lastName,
                                          @RequestParam String nickName, @RequestParam String password,
                                          @RequestParam String email, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob,
                                          @RequestParam Boolean gender, @RequestParam String role) {
        User newUser = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        newUser.setPassword(hashedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setNickName(nickName);
        newUser.setEmail(email);
        newUser.setDob(dob);
        newUser.setGender(gender);
        newUser.setRoleType(User.RoleTypes.valueOf(role));
        userRepository.save(newUser);
        return newUser;
    }

    //update user
    @PutMapping(path="/{id}")
    public @ResponseBody User updateUser (@PathVariable("id") Long id, @RequestParam String firstName,
                                          @RequestParam String lastName, @RequestParam String nickName,
                                          @RequestParam String password, @RequestParam String email,
                                          @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob, @RequestParam Boolean gender,
                                          @RequestParam String role) {
        User updatedUser = userRepository.findById(id).get();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        updatedUser.setPassword(hashedPassword);
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setNickName(nickName);
        updatedUser.setEmail(email);
        updatedUser.setDob(dob);
        updatedUser.setGender(gender);
        updatedUser.setRoleType(User.RoleTypes.valueOf(role));
        userRepository.save(updatedUser);
        return updatedUser;
    }

    //delete user by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody Iterable<User> deleteUser (@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return userRepository.findAll();
    }

}