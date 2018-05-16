package org.flow.controllers;


import org.flow.models.User;
import org.flow.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path="/pubapp")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/")
    public String render() {
        return "Hello there!";
    }

    @GetMapping(path="/add")
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
        return "Saved";
    }
}
