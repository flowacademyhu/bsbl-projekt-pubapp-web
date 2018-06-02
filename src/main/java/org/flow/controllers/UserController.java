package org.flow.controllers;


import com.google.common.net.HttpHeaders;
import org.flow.configuration.Timeout;
import org.flow.models.Achievement;
import org.flow.models.User;
import org.flow.repositories.AchievementRepository;
import org.flow.repositories.SessionRepository;
import org.flow.repositories.UserAchievementRepository;
import org.flow.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    Timeout timeout = new Timeout();

    //get all users
    @GetMapping
    public @ResponseBody ResponseEntity getAllUsers () {
        return ResponseEntity.ok(userRepository.findAll());
    }

    //get user by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity getUserById (@PathVariable("id") Long id, @RequestHeader(value = "Authorization") String token)  throws UserNotFoundException {
        if(timeout.stayingALive(token)) {
            if (checkUser(id, token)) {
                Optional<User> user = userRepository.findById(id);
                if (!user.isPresent()) {
                    //throw new UserNotFoundException("User not found.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
                }
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
            }
        } else  {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session timeout.");
        }
    }


    //create new user
    @PostMapping
    public @ResponseBody ResponseEntity addNewUser (@RequestBody String user) {
        User newUser = new User();
        JSONObject jsonObject = new JSONObject(user);
        if(userRepository.findByEmail(jsonObject.getString("email")) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This e-mail address is already taken.");
        } else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(jsonObject.getString("password"));
            newUser.setPassword(hashedPassword);
            newUser.setFirstName(jsonObject.getString(  "firstName"));
            newUser.setLastName(jsonObject.getString("lastName"));
            newUser.setNickName(jsonObject.getString("nickName"));
            newUser.setEmail(jsonObject.getString("email"));
            newUser.setRoleType(jsonObject.getString("role"));
            Date dob = null;
            try {
                dob = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("dob"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newUser.setDob(dob);
            newUser.setGender(jsonObject.getBoolean("gender"));
            userRepository.save(newUser);
            return ResponseEntity.ok(newUser);
        }
    }

    //update user
    @PutMapping(path="/{id}")
    public @ResponseBody ResponseEntity updateUser (@PathVariable("id") Long id, @RequestBody String user, @RequestHeader(value = "Authorization") String token) {
        if(timeout.stayingALive(token)) {
            if(checkUser(id, token)) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                JSONObject jsonObject = new JSONObject(user);
                User updatedUser = userRepository.findById(id).get();
                if(passwordEncoder.matches(jsonObject.getString("oldPassword"), updatedUser.getPassword())) {
                    if(!jsonObject.getString("newPassword").equals("") && jsonObject.getString("newPassword") != null) {
                        String hashedPassword = passwordEncoder.encode(jsonObject.getString("newPassword"));
                        updatedUser.setPassword(hashedPassword);
                    }
                    updatedUser.setFirstName(jsonObject.getString("firstName"));
                    updatedUser.setLastName(jsonObject.getString("lastName"));
                    updatedUser.setNickName(jsonObject.getString("nickName"));
                    updatedUser.setEmail(jsonObject.getString("email"));
                    Date dob = null;
                    try {
                        dob = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("dob"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    updatedUser.setDob(dob);
                    updatedUser.setGender(jsonObject.getBoolean("gender"));
                    userRepository.save(updatedUser);
                    return ResponseEntity.ok(updatedUser);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session timeout.");
        }
    }

    //delete user by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteUser (@PathVariable("id") Long id, @RequestHeader(value = "Authorization")  String token) {
        if(checkUser(id, token)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }

    public boolean checkUser(Long id, String token) {
        return sessionRepository.findByToken(token).getUser().getId() == id;
    }

    public boolean isAdmin(String token) {
        return String.valueOf(sessionRepository.findByToken(token).getUser().getRoleType()).equals("ADMIN");
    }
}
