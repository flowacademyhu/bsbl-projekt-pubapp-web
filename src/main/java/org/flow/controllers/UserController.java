package org.flow.controllers;


import com.google.common.net.HttpHeaders;
import org.flow.models.Achievement;
import org.flow.models.User;
import org.flow.models.UserAchievement;
import org.flow.repositories.AchievementRepository;
import org.flow.repositories.SessionRepository;
import org.flow.repositories.UserAchievementRepository;
import org.flow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAchievementRepository userAchievementRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private AchievementRepository achievementRepository;

    //get all users
    @GetMapping(path="/")
    public @ResponseBody Iterable<User> getAllUsers () {
        return userRepository.findAll();
    }

    //get user by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity getUserById (@PathVariable("id") Long id, HttpServletRequest httpServletRequest)  throws UserNotFoundException {
        if(checkUser(id, httpServletRequest)) {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new UserNotFoundException("User not found.");
            }
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    //create new user
    @PostMapping(path="/")
    public @ResponseBody ResponseEntity addNewUser (@RequestParam String firstName, @RequestParam String lastName,
                                          @RequestParam String nickName, @RequestParam String password,
                                          @RequestParam String email, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob,
                                          @RequestParam Boolean gender, @RequestParam String role, HttpServletRequest httpServletRequest) {
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
        return ResponseEntity.ok(newUser);
    }

    //update user
    @PutMapping(path="/{id}")
    public @ResponseBody ResponseEntity updateUser (@PathVariable("id") Long id, @RequestParam String firstName,
                                          @RequestParam String lastName, @RequestParam String nickName,
                                          @RequestParam String password, @RequestParam String email,
                                          @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date dob,
                                          @RequestParam Boolean gender, HttpServletRequest httpServletRequest) {
        if(checkUser(id, httpServletRequest)) {
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
            userRepository.save(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    //delete user by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteUser (@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        if(checkUser(id, httpServletRequest)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    public boolean checkUser(Long id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authentication");
        return sessionRepository.findByToken(token).getUser().getId() == id;
    }
}
