package com.cbc.design.auth.repositories;

import com.cbc.design.auth.domain.GoodCritic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by cbc on 2018/4/2.
 */
@Repository
public interface GoodCriticRepository extends JpaRepository<GoodCritic, Long> {

    Long countByPublishCriticIdAndIsAllowTrue(Long publishCriticId);

    Boolean existsByUserIdAndPublishCriticIdAndIsAllowTrue(Long userId,Long publishCriticId);

    Long countByUserIdAndIsAllowTrue(Long userId);
}
