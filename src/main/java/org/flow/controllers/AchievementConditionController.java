package org.flow.controllers;

        import org.flow.models.AchievementCondition;
        import org.flow.repositories.AchievementConditionRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.format.annotation.DateTimeFormat;
        import org.springframework.web.bind.annotation.*;

        import java.util.Date;
        import java.util.Optional;

@RestController
@RequestMapping(path="/achievements_condition")
public class AchievementConditionController {

    @Autowired
    private AchievementConditionRepository achievementConditionRepository;

    //get all achievementConditions
    @GetMapping(path = "/")
    public Iterable<AchievementCondition> findAllAchievementConditions() {
        return achievementConditionRepository.findAll();
    }



}
