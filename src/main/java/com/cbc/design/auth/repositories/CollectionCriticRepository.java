package com.cbc.design.auth.repositories;

import com.cbc.design.auth.domain.CollectionCritic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cbc on 2018/4/1.
 */
@Repository
public interface CollectionCriticRepository extends JpaRepository<CollectionCritic,Long> {

    Long countByPublishCriticIdAndIsAllowTrue(Long PublishCriticId);

    Boolean existsByUserIdAndPublishCriticIdAndIsAllowTrue(Long user,Long PublishCriticId);

    Long countByUserIdAndIsAllowTrue(Long userId);
}
