package org.flow.controllers;

import org.flow.models.Achievement;
import org.flow.models.AchievementCondition;
import org.flow.repositories.AchievementConditionRepository;
import org.flow.repositories.AchievementRepository;
import org.flow.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private AchievementConditionRepository achievementConditionRepository;
    @Autowired
    private ProductRepository productRepository;

    //get achievement conditions
    @GetMapping(path = "/{id}/achievement_conditions")
    public Iterable<AchievementCondition> findAchievementConditions(@PathVariable("id") Long id) {
        Iterable<AchievementCondition> allAchievementConditions = achievementConditionRepository.findAll();
        List<AchievementCondition> achievementConditionList = new ArrayList();
        for (AchievementCondition achievementCondition : allAchievementConditions) {
            if (achievementCondition.getAchievement().getId().equals(achievementRepository.findById(id).get().getId())) {
                achievementConditionList.add(achievementCondition);
            }
        }
        Iterable<AchievementCondition> conditions = achievementConditionList;
        return conditions;
    }

    //get achievement condition by ID
    @GetMapping(path = "/{id}/achievement_conditions/{id2}")
    public @ResponseBody AchievementCondition getAchievementConditionById(@PathVariable("id") Long id2) throws AchievementNotFoundException {
        Optional<AchievementCondition> achievementCondition = achievementConditionRepository.findById(id2);
        return achievementCondition.get();
    }

    //create new achievement condition
    @PostMapping(path="/{id}/achievement_conditions")
    public @ResponseBody AchievementCondition addNewAchievementCondition (@PathVariable("id") Long id,
                                                                          @RequestParam Integer quantity,
                                                                          @RequestParam String productName) {
        AchievementCondition newAchievementCondition = new AchievementCondition();
        newAchievementCondition.setQuantity(quantity);
        newAchievementCondition.setAchievement(achievementRepository.findById(id).get());
        newAchievementCondition.setProduct(productRepository.findByName(productName));
        achievementConditionRepository.save(newAchievementCondition);
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
    public @ResponseBody Iterable<AchievementCondition> deleteAchievementCondition(@PathVariable("id2") Long id2) {
        achievementConditionRepository.deleteById(id2);
        return achievementConditionRepository.findAll();
    }
}