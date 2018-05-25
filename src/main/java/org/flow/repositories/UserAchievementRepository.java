package org.flow.repositories;

import org.flow.models.Achievement;
import org.flow.models.UserAchievement;
import org.springframework.data.repository.CrudRepository;

public interface UserAchievementRepository extends CrudRepository<UserAchievement, Long>{
}
