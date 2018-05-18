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



//http://localhost:8080/users/registration?password=1234&firstName=istvan&lastName=nagy&nickName=tyson&email=jesus@email.com&dob=1900-01-01&gender=true
@RestController
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get all users
    @GetMapping(path="/")
    public Iterable<User> findAllUsers () {
        return userRepository.findAll();
    }


    //create new user
    @PostMapping(path="/registration")
    public @ResponseBody String addNewUser (@RequestParam String password, @RequestParam String firstName, @RequestParam String lastName,
                       @RequestParam String nickName, @RequestParam String email,
                       @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob, @RequestParam Boolean gender) {

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
        userRepository.save(newUser);
        return "userById";
    }


    //edit user by ID
    @PutMapping(path = "/{id}/edit")
        public @ResponseBody String updateUser(@RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String nickName, @RequestParam String email,
                                 @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob, @RequestParam Boolean gender,
                                 @PathVariable long id) throws UserNotFoundException {

        Optional<User> editedUser = userRepository.findById(id);
        if (!editedUser.isPresent()) {
            throw new UserNotFoundException("User not found.");
        }

        User user = editedUser.get();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickName(nickName);
        user.setEmail(email);
        user.setDob(dob);
        user.setGender(gender);
        return "User updated.";
        }

    //get user by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<User> getUser (@PathVariable long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found.");
        }

        return user;
    }


    //delete user by ID
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
        return id + "deleted";
    }

}
