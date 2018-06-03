package org.flow.controllers;

import org.flow.configuration.Validations;
import org.flow.models.*;
import org.flow.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/users")
public class UserAchievementController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAchievementRepository userAchievementRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private AchievementConditionRepository achievementConditionRepository;
    @Autowired
    private OrderingRepository orderingRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    Validations validations = new Validations();

    @GetMapping(path = "/{id}/user_achievements")
    public @ResponseBody
    ResponseEntity getUserAchievemnets(@PathVariable("id") Long id, @RequestHeader(value = "Authorization") String token) {
        if (validations.checkUser(id, token)) {
            Iterable<UserAchievement> allUserAchievements = userAchievementRepository.findAll();
            List<UserAchievement> userAchievements = new ArrayList<>();
            List<Optional<Achievement>> achievements = new ArrayList<>();
            for (UserAchievement userAchievement : allUserAchievements) {
                if (userAchievement.getUser().getId().equals(userRepository.findById(id).get().getId())) {
                    userAchievements.add(userAchievement);
                }
            }
            for (UserAchievement userAchievement : userAchievements) {
                achievements.add(achievementRepository.findById(userAchievement.getAchievement().getId()));

            }
            Iterable<Optional<Achievement>> ownAchievements = achievements;
            return ResponseEntity.ok(ownAchievements);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path = "/{id}")
    public @ResponseBody
    ResponseEntity createUserAchievement(@PathVariable("id") Long user_id,
                                         @RequestParam Long order_id) {
        User user = userRepository.findById(user_id).get();
        int userXP = user.getXp();
        Ordering order = orderingRepository.findById(order_id).get();
        Iterable<OrderLine> allOrderlines = orderLineRepository.findAll();
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLine orderLine : allOrderlines) {
            if (orderLine.getOrdering().getId().equals(order.getId())) {
                orderLines.add(orderLine);
                userXP += orderLine.getProduct().getXpValue() * orderLine.getQuantity();
            }
        }
        Date now = new Date();
        List<Achievement> completedAchievements = new ArrayList<>();
        Iterable<Achievement> activeAchievements = achievementRepository.findByExpirationAfter(now);
        Iterable<AchievementCondition> allAchievementConditions = achievementConditionRepository.findAll();
        for (Achievement achievement : activeAchievements) {
            List<AchievementCondition> conditions = new ArrayList<>();
            for (AchievementCondition achievementCondition : allAchievementConditions) {
                if (achievementCondition.getAchievement().getId().equals(achievement.getId())) {
                    conditions.add(achievementCondition);
                }
            }
            int numberOfConditions = 0;
            for (AchievementCondition condition : conditions) {
                for (OrderLine orderLine : orderLines) {
                    if (condition.getProduct().getId().equals(orderLine.getProduct().getId()) &&
                            condition.getQuantity() <= orderLine.getQuantity()) {
                        numberOfConditions++;
                    }
                }
            }
            if (conditions.size() == numberOfConditions) {
                completedAchievements.add(achievement);
            }
        }
        Iterable<UserAchievement> allUserAchievements = userAchievementRepository.findAll();
        List<UserAchievement> currentUserAchievements = new ArrayList<>();
        List<Achievement> actuallyCompletedAchievements = new ArrayList<>();
        for (UserAchievement userAchievement : allUserAchievements) {
            if (userAchievement.getUser().getId().equals(userRepository.findById(user_id).get().getId())) {
                currentUserAchievements.add(userAchievement);
            }
        }
        if (currentUserAchievements.size() == 0) {
            for (Achievement achievement : completedAchievements) {
                actuallyCompletedAchievements.add(achievement);
            }
        } else {
            List<Achievement> alreadyCompletedAchievements = new ArrayList<>();
            for(UserAchievement userAchievement : currentUserAchievements) {
                alreadyCompletedAchievements.add(userAchievement.getAchievement());
            }
            for (Achievement achievement : completedAchievements) {
                if(!alreadyCompletedAchievements.contains(achievement)) {
                    completedAchievements.add(achievement);
                }
            }
        }
        for (Achievement achievement : actuallyCompletedAchievements) {
            UserAchievement newUserAchievement = new UserAchievement();
            newUserAchievement.setUser(userRepository.findById(user_id).get());
            newUserAchievement.setAchievement(achievement);
            userAchievementRepository.save(newUserAchievement);
            userXP += achievement.getXpValue();
        }
        user.setXp(userXP);
        userRepository.save(user);
        return ResponseEntity.ok("Achievements added.");
    }
}
