package org.flow.controllers;

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

    UserController userController = new UserController();

    @GetMapping(path= "/{id}/user_achievements")
    public @ResponseBody
    ResponseEntity getUserAchievemnets(@PathVariable("id") Long id, @RequestHeader String token) {
        if(userController.checkUser(id, token)) {
            Iterable<UserAchievement> allUserAchievements = userAchievementRepository.findAll();
            List<UserAchievement> userAchievements = new ArrayList<>();
            List<Optional<Achievement>> achievements = new ArrayList<>();
            for(UserAchievement userAchievement : allUserAchievements) {
                if(userAchievement.getUser().getId().equals(userRepository.findById(id).get().getId())) {
                    userAchievements.add(userAchievement);
                }
            }
            for(UserAchievement userAchievement : userAchievements) {
                achievements.add(achievementRepository.findById(userAchievement.getAchievement().getId()));

            }
            Iterable<Optional<Achievement>> ownAchievements = achievements;
            return ResponseEntity.ok(ownAchievements);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*
    @PostMapping(path="/{id}")
    public @ResponseBody ResponseEntity createUserAchievement(@PathVariable("id") Long id, HttpServletRequest httpServletRequest,
                                                              @RequestParam Long order_id) {
        if(userController.checkUser(id, httpServletRequest)) {
            Ordering order = orderingRepository.findById(order_id).get();
            Iterable<OrderLine> allOrderlines = orderLineRepository.findAll();
            List<OrderLine> orderLines = new ArrayList<>();
            for(OrderLine  orderLine: allOrderlines) {
                if(orderLine.getOrdering().getId().equals(order.getId())) {
                    orderLines.add(orderLine);
                }
            }

            Date now = new Date();
            Iterable<AchievementCondition> activeAchievementConditions = achievementConditionRepository.findByExpirationAfter(now);
            for (Achievement achievement : activeAchievements) {
            // kell egy find by achivement id is. ha az egyik condition teljesült, akkor meg kell nezni azt is,
                // hogy van-e tovabbi condotion ugyanazzal az achievement id-val

            }
            List<UserAchievement> userAchievements = new ArrayList<>();
            List<Optional<Achievement>> achievements = new ArrayList<>();
            for(UserAchievement userAchievement : allUserAchievements) {
                if(userAchievement.getUser().getId().equals(userRepository.findById(id).get().getId())) {
                    userAchievements.add(userAchievement);
                }
            }
            for(UserAchievement userAchievement : userAchievements) {
                achievements.add(achievementRepository.findById(userAchievement.getAchievement().getId()));

            }
            Iterable<Optional<Achievement>> ownAchievements = achievements;
        }
    }
    */
}
