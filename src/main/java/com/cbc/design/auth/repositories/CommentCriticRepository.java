package com.cbc.design.auth.repositories;

import com.cbc.design.auth.domain.CommentCritic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by cbc on 2018/4/2.
 */
@Repository
public interface CommentCriticRepository extends JpaRepository<CommentCritic,Long> {

        Long countByPublishCriticIdAndIsAllowTrue(Long publishCriticId);

        Long countByUserIdAndIsAllowTrue(Long userId);

        @Query("select c from CommentCritic c left join User u on c.userId = u.id where c.publishCriticId = ?1")
        Page<CommentCritic> findByPublishCriticId(Long publishCriticId, Pageable pageable);
}
