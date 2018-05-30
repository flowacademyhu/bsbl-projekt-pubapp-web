package org.flow.controllers;


import com.google.common.net.HttpHeaders;
import org.flow.models.Achievement;
import org.flow.models.User;
import org.flow.models.UserAchievement;
import org.flow.repositories.AchievementRepository;
import org.flow.repositories.SessionRepository;
import org.flow.repositories.UserAchievementRepository;
import org.flow.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    //get all users
    @GetMapping(path="/")
    public @ResponseBody ResponseEntity getAllUsers () {
        return ResponseEntity.ok(userRepository.findAll());
    }

    //get user by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity getUserById (@PathVariable("id") Long id, HttpServletRequest httpServletRequest)  throws UserNotFoundException {
        if(checkUser(id, httpServletRequest)) {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                //throw new UserNotFoundException("User not found.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
            }
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }
    }


    //create new user
    @PostMapping
    public @ResponseBody ResponseEntity addNewUser (@RequestBody String user) {
        User newUser = new User();
        JSONObject jsonObject = new JSONObject(user);
        System.out.println(jsonObject.getString("firstName"));
        System.out.println(jsonObject.getString("lastName"));
        System.out.println(jsonObject.getString("password"));
        System.out.println(jsonObject.getString("nickName"));
        System.out.println(jsonObject.getString("email"));
        System.out.println(jsonObject.getString("dob"));
        System.out.println(jsonObject.getBoolean("gender"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(jsonObject.getString("password"));
        newUser.setPassword(hashedPassword);
        newUser.setFirstName(jsonObject.getString("firstName"));
        newUser.setLastName(jsonObject.getString("lastName"));
        newUser.setNickName(jsonObject.getString("nickName"));
        newUser.setEmail(jsonObject.getString("email"));
        Date dob = null;
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("dob"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newUser.setDob(dob);
        newUser.setGender(jsonObject.getBoolean("gender"));
        newUser.setRoleType(User.RoleTypes.valueOf("USER"));
        userRepository.save(newUser);
        return ResponseEntity.ok(newUser);
    }

    //update user
    @PutMapping(path="/{id}")
    public @ResponseBody ResponseEntity updateUser (@PathVariable("id") Long id, @RequestBody String user, HttpServletRequest httpServletRequest) {
        if(checkUser(id, httpServletRequest)) {
            User updatedUser = userRepository.findById(id).get();
            JSONObject jsonObject = new JSONObject(user);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(jsonObject.getString("password"));
            updatedUser.setPassword(hashedPassword);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }

    //delete user by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteUser (@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        if(checkUser(id, httpServletRequest)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You shall not pass.");
        }

    }

    public boolean checkUser(Long id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authentication");
        return sessionRepository.findByToken(token).getUser().getId() == id;
    }
}
