package org.flow.repositories;

import org.flow.models.Achievement;
import org.flow.models.AchievementCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;

public interface AchievementConditionRepository extends CrudRepository<AchievementCondition, Long> {
    Iterable<AchievementCondition> findByAchievementId(Long id, org.springframework.data.domain.Pageable pageable);
}
