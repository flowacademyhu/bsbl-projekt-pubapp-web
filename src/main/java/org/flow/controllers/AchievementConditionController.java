package org.flow.controllers;

        import org.flow.models.AchievementCondition;
        import org.flow.repositories.AchievementConditionRepository;
        import org.flow.repositories.AchievementRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/achievements/{id}/achievementConditions")
public class AchievementConditionController {

    @Autowired
    private AchievementConditionRepository achievementConditionRepository;
    private AchievementRepository achievementRepository;

    //get all achievementConditions
    @GetMapping(path="/")
    public String getAllAchievements (Model model) {
        //where achievementConditions.achievement_id = {id}
        model.addAttribute("achievementCondition", achievementConditionRepository.findAll());
        return "achievementConditionList";
    }

    //get achievementCondition by ID
    @GetMapping(path = "/{id2}")
    public String getAchievementConditionById (@PathVariable("id2") long id, Model model) {
        model.addAttribute("achievementCondition", achievementConditionRepository.findById(id).get());
        return "achievementCondition";
    }


    //get create achievementCondition form
    @GetMapping(path="/new")
    public String newAchievementCondition(Model model) {
        model.addAttribute("achievementCondition", new AchievementCondition());
        return "achievementConditionNew";
    }


    //create new achievementCondition
    @PostMapping(path="/")
    public @ResponseBody String addNewAchievementCondition (@PathVariable("id") long id, @ModelAttribute AchievementCondition achievementCondition, Model model) {
        AchievementCondition newAchievementCondition = new AchievementCondition();
        newAchievementCondition.setAchievement(achievementRepository.findById(id).get());
        newAchievementCondition.setProduct(achievementCondition.getProduct());
        newAchievementCondition.setQuantity(achievementCondition.getQuantity());
        achievementConditionRepository.save(newAchievementCondition);
        model.addAttribute("achievementCondition", achievementConditionRepository.findAll());
        return "achievementConditionsList";
    }


    //get achievementCondition edit form
    @GetMapping(path="/{id}/edit")
    public String editAchievementCondition(@PathVariable("id") long id, Model model) {
        model.addAttribute("achievementCondition", achievementConditionRepository.findById(id).get());
        return "achievementConditionEdit";
    }

    //update achievementCondition
    @PostMapping(path="/{id}/")
    public String updateAchievementCondition(@PathVariable("id") long id, @ModelAttribute AchievementCondition achievementCondition, Model model) {
        AchievementCondition updatedAchievementCondition = achievementConditionRepository.findById(id).get();
        updatedAchievementCondition.setProduct(achievementCondition.getProduct());
        updatedAchievementCondition.setQuantity(achievementCondition.getQuantity());
        achievementConditionRepository.save(updatedAchievementCondition);
        model.addAttribute("achievementCondition", achievementConditionRepository.findAll());
        return "achievementConditionList";
    }



    //delete achievementCondition by ID
    @PostMapping(path = "/{id}")
    public String deleteAchievementCondition(@PathVariable("id") long id, Model model) {
        achievementConditionRepository.deleteById(id);
        model.addAttribute("achievementCondition", achievementConditionRepository.findAll());
        return "achievementConditionList";
    }

}





