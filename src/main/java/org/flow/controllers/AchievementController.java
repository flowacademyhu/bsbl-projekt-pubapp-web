package org.flow.controllers;

import org.flow.models.Achievement;
import org.flow.repositories.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/achievements")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;


    //get all users
    @GetMapping(path="/")
    public String getAllAchievements (Model model) {
        model.addAttribute("user", achievementRepository.findAll());
        return "achievementList";
    }

    //get user by ID
    @GetMapping(path = "/{id}")
    public String getAchievementById (@PathVariable("id") long id, Model model)  throws AchievementNotFoundException {
        Optional<Achievement> achievement = achievementRepository.findById(id);
        if (!achievement.isPresent()) {
            throw new AchievementNotFoundException("Achievement not found.");
        }

        model.addAttribute("achievement", achievementRepository.findById(id).get());
        return "achievement";
    }


    //get achievement user form
    @GetMapping(path="/new")
    public String newAchievement(Model model) {
        model.addAttribute("achievement", new Achievement());
        return "achievementNew";
    }


    //create new achievement
    @PostMapping(path="/new")
    public @ResponseBody String addNewAchievement (@ModelAttribute Achievement achievement, Model model) {
        Achievement newAchievement = new Achievement();
        newAchievement.setName(achievement.getName());
        newAchievement.setDescription(achievement.getDescription());
        newAchievement.setXpValue(achievement.getXpValue());
        newAchievement.setExpiration(achievement.getExpiration());
        achievementRepository.save(newAchievement);
        model.addAttribute("achievement", achievementRepository.findAll());
        return "achievementList";
    }


    //get achievement edit form
    @GetMapping(path="/{id}/edit")
    public String editAchievement(@PathVariable("id") long id, Model model) {
        model.addAttribute("achievement", achievementRepository.findById(id).get());
        return "achievementEdit";
    }

    //update achievement
    @PostMapping(path="/{id}/edit")
    public String updateAchievement(@PathVariable("id") long id, @ModelAttribute Achievement achievement,
                             Model model) {
        Achievement updatedAchievement = achievementRepository.findById(id).get();
        updatedAchievement.setName(achievement.getName());
        updatedAchievement.setDescription(achievement.getDescription());
        updatedAchievement.setXpValue(achievement.getXpValue());
        updatedAchievement.setExpiration(achievement.getExpiration());
        achievementRepository.save(updatedAchievement);
        model.addAttribute("achievement", achievementRepository.findAll());
        return "achievementList";
    }



    //delete achievement by ID
    @PostMapping(path = "/{id}")
    public String deleteAchievement(@PathVariable("id") long id, Model model) {
        achievementRepository.deleteById(id);
        model.addAttribute("achievement", achievementRepository.findAll());
        return "achievementList";
    }




}
