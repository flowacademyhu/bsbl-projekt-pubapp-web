package org.flow.controllers;

import org.flow.models.Achievement;
import org.flow.models.AchievementCondition;
import org.flow.repositories.AchievementConditionRepository;
import org.flow.repositories.AchievementRepository;
import org.flow.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path="/achievements")
public class AchievementConditionController {

    @Autowired
    private AchievementRepository achievementRepository;
    private AchievementConditionRepository achievementConditionRepository;
    private ProductRepository productRepository;

    //get achievement conditions
    @GetMapping(path = "/{id}/achievement_conditions")
    public List<AchievementCondition> findAchievementConditions(@PathVariable("id") Long id) {
        Achievement achievement = achievementRepository.findById(id).get();
        List<AchievementCondition> conditions = achievement.getAchievementConditionList();
        return conditions;
    }

    //get achievement condition by ID
    @GetMapping(path = "/{id}/achievement_conditions/{id2}")
    public @ResponseBody
    AchievementCondition getAchievementConditionById(@PathVariable("id") Long id, @PathVariable("id") Long id2) throws AchievementNotFoundException {
        Optional<Achievement> achievement = achievementRepository.findById(id);
        Optional<AchievementCondition> achievementCondition = achievementConditionRepository.findById(id2);
        return achievementCondition.get();
    }

    //create new achievement condition
    @PostMapping(path="/{id}/achievement_conditions")
    public @ResponseBody AchievementCondition addNewAchievementCondition (@PathVariable("id") Long id,
                                                                          @RequestParam Integer quantity,
                                                                          @RequestParam String productName) {
        Achievement achievement = achievementRepository.findById(id).get();
        AchievementCondition newAchievementCondition = new AchievementCondition();
        newAchievementCondition.setQuantity(quantity);
        newAchievementCondition.setAchievement(achievementRepository.findById(id).get());
        newAchievementCondition.setProduct(productRepository.findByName(productName));
        List<AchievementCondition> achievementConditionList = achievement.getAchievementConditionList();
        achievementConditionRepository.save(newAchievementCondition);
        achievementConditionList.add(newAchievementCondition);
        achievement.setAchievementConditionList(achievementConditionList);
        return newAchievementCondition;
    }

    //update achievement condition
    @PutMapping(path="/{id}/achievement_conditions/{id2}")
    public @ResponseBody AchievementCondition updateAchievementCondition(@PathVariable("id2") Long id,
                                                                         @RequestParam Integer quantity,
                                                                         @RequestParam String productName) {
        AchievementCondition achievementCondition = achievementConditionRepository.findById(id).get();
        achievementCondition.setQuantity(quantity);
        achievementCondition.setProduct(productRepository.findByName(productName));
        achievementConditionRepository.save(achievementCondition);
        return achievementCondition;
    }

    //delete achievement condition
    @DeleteMapping(path="/{id}/achievement_conditions/{id2}")
    public @ResponseBody Iterable<AchievementCondition> deleteAchievementCondition(@PathVariable("id") Long id, @PathVariable("id2") Long id2) {
        Achievement achievement = achievementRepository.findById(id).get();
        List<AchievementCondition> achievementConditionList = achievement.getAchievementConditionList();
        achievementConditionList.remove(achievementConditionRepository.findById(id2));
        achievement.setAchievementConditionList(achievementConditionList);
        achievementConditionRepository.deleteById(id2);
        return achievementConditionRepository.findAll();
    }
}