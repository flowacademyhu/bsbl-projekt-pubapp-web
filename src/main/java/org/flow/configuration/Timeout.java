package org.flow.configuration;

import org.flow.models.Session;
import org.flow.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Timeout {

    @Autowired
    private SessionRepository sessionRepository;

    public boolean stayingALive(String token) {
        Date date = new Date();
        if(sessionRepository.findByToken(token).getExpiration().before(date)) {
            sessionRepository.delete(sessionRepository.findByToken(token));
            return false;
        } else {
            date.setTime(date.getTime() + 300000);
            Session updatedSession = sessionRepository.findByToken(token);
            updatedSession.setExpiration(date);
            sessionRepository.save(updatedSession);
            return true;
        }
    }
}
