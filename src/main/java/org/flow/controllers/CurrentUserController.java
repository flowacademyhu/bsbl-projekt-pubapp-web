package org.flow.controllers;


import org.flow.models.User;
import org.flow.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/users/current")

public class CurrentUserController {

    @Autowired

    private UserRepository userRepository;

    @GetMapping(
            path = {"/{id}"}
    )

    //current user show
    @ResponseBody
    public Optional<User> getUser(@PathVariable long id) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found.");
        } else {
            return user;
        }
    }



    // current user deleted
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        Optional<User> user = userRepository.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    // current user update
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(
            @PathVariable("id") long id, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String nickName, @RequestParam String email,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob, @RequestParam Boolean gender){
        System.out.println("Updating User " + id);

        User currentUser = userRepository.findById(id).get();

        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        currentUser.setPassword(password);
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setNickName(nickName);
        currentUser.setEmail(email);
        currentUser.setDob(dob);
        currentUser.setGender(gender);
        userRepository.save(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }


}
