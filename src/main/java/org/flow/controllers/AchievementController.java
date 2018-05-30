package org.flow.controllers;

import org.flow.models.Achievement;
import org.flow.repositories.AchievementRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/achievements")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;

    //get all achievements
    @GetMapping
    public @ResponseBody ResponseEntity getAllAchievements() {
        return ResponseEntity.ok(achievementRepository.findAll());
    }

    //get achievement by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody ResponseEntity getAchievementById (@PathVariable("id") Long id)  throws AchievementNotFoundException {
        Optional<Achievement> achievement = achievementRepository.findById(id);
        if (!achievement.isPresent()) {
            //throw new AchievementNotFoundException("Achievement not found.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
        return ResponseEntity.ok(achievement);
    }

    //create new achievement
    @PostMapping
    public @ResponseBody ResponseEntity addNewAchievement (@RequestBody String achievement) {
        JSONObject jsonObject = new JSONObject(achievement);
        Achievement newAchievement = new Achievement();
        newAchievement.setName(jsonObject.getString("name"));
        newAchievement.setDescription(jsonObject.getString("description"));
        newAchievement.setXpValue(jsonObject.getInt("xpValue"));
        Date expiration = null;
        try {
            expiration = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("expiration"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newAchievement.setExpiration(expiration);
        achievementRepository.save(newAchievement);
        return ResponseEntity.ok(newAchievement);
    }

    //update achievement
    @PutMapping(path="/{id}")
    public @ResponseBody ResponseEntity updateAchievement(@PathVariable("id") Long id, @RequestBody String achievement) {
        Achievement updatedAchievement = achievementRepository.findById(id).get();
        JSONObject jsonObject = new JSONObject(achievement);
        updatedAchievement.setName(jsonObject.getString("name"));
        updatedAchievement.setDescription(jsonObject.getString("description"));
        updatedAchievement.setXpValue(jsonObject.getInt("xpValue"));
        Date expiration = null;
        try {
            expiration = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("expiration"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        updatedAchievement.setExpiration(expiration);
        achievementRepository.save(updatedAchievement);
        return ResponseEntity.ok(updatedAchievement);
    }

    //delete achievement by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody ResponseEntity deleteAchievement(@PathVariable("id") Long id) {
        achievementRepository.deleteById(id);
        return ResponseEntity.ok(achievementRepository.findAll());
    }
}
