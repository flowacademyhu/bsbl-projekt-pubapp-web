package org.flow.controllers;

import org.flow.models.Session;
import org.flow.models.User;
import org.flow.repositories.SessionRepository;
import org.flow.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping(path="/sessions")
public class SessionController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping
    public @ResponseBody ResponseEntity login(@RequestBody String login) {
        JSONObject jsonObject = new JSONObject(login);
        User loggedUser = userRepository.findByEmail(jsonObject.getString("email"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(jsonObject.getString("password"), loggedUser.getPassword())) {
            String token = UUID.randomUUID().toString();
            Session session = new Session();
            session.setToken(token);
            session.setUser(loggedUser);
            Date date = new Date();
            date.setTime(date.getTime() + 1000000);
            session.setExpiration(date);
            sessionRepository.save(session);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody ResponseEntity logout(@PathVariable("id") Long id, @RequestHeader String token) {
        sessionRepository.delete(sessionRepository.findByToken(token));
        return ResponseEntity.ok("LOGGED OUT");
    }

    /*
    @RequestMapping
    public void stayingALive(@RequestHeader String token) {
        Date date = new Date();
        if(sessionRepository.findByToken(token).getExpiration().before(date)) {
            sessionRepository.delete(sessionRepository.findByToken(token));
        } else {
            date.setTime(date.getTime() + 1000000);
            sessionRepository.findByToken(token).setExpiration(date);
        }
    }
    */
}
