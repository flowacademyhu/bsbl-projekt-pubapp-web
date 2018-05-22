package org.flow.controllers;

import org.flow.models.Achievement;
import org.flow.repositories.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/achievements")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;

    //get all achievements
    @GetMapping(path="/")
    public @ResponseBody Iterable<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    //get achievement by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody Achievement getAchievementById (@PathVariable("id") Long id)  throws AchievementNotFoundException {
        Optional<Achievement> achievement = achievementRepository.findById(id);
        if (!achievement.isPresent()) {
            throw new AchievementNotFoundException("Achievement not found.");
        }
        return achievement.get();
    }

    //create new achievement
    @PostMapping(path="/")
    public @ResponseBody Achievement addNewAchievement (@RequestParam String name, @RequestParam String description,
                                                   @RequestParam Integer xpValue,
                                                   @RequestParam @DateTimeFormat(pattern= "yyyy-MM-dd") Date expiration) {
        Achievement newAchievement = new Achievement();
        newAchievement.setName(name);
        newAchievement.setDescription(description);
        newAchievement.setXpValue(xpValue);
        newAchievement.setExpiration(expiration);
        achievementRepository.save(newAchievement);
        return newAchievement;
    }

    //update achievement
    @PutMapping(path="/{id}/")
    public @ResponseBody Achievement updateAchievement(@PathVariable("id") Long id, @RequestParam String name,
                                                       @RequestParam String description, @RequestParam Integer xpValue,
                                                       @RequestParam @DateTimeFormat(pattern= "yyyy-MM-dd") Date expiration) {
        Achievement updatedAchievement = achievementRepository.findById(id).get();
        updatedAchievement.setName(name);
        updatedAchievement.setDescription(description);
        updatedAchievement.setXpValue(xpValue);
        updatedAchievement.setExpiration(expiration);
        achievementRepository.save(updatedAchievement);
        return updatedAchievement;
    }

    //delete achievement by ID
    @DeleteMapping(path = "/{id}")
    public @ResponseBody Iterable<Achievement> deleteAchievement(@PathVariable("id") Long id) {
        achievementRepository.deleteById(id);
        return achievementRepository.findAll();
    }
}
