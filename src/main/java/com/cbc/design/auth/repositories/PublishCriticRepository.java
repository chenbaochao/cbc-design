package com.cbc.design.auth.repositories;

import com.cbc.design.auth.domain.PublishCritic;
import com.cbc.design.auth.web.dto.UserPublishDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cbc on 2018/4/1.
 */
@Repository
public interface PublishCriticRepository extends JpaRepository<PublishCritic,Long> {

    @Query("select new com.cbc.design.auth.web.dto.UserPublishDTO(u,pc) from User u left join PublishCritic pc on u.id = pc.userId where pc.userId in ?1 and pc.isAllow = 1 order by pc.updateDate desc, pc.id desc")
    Page<UserPublishDTO> findByUserIdInOrderByUpdateDateDesc(List<Long> userIds, Pageable pageable);

    Long countByUserIdAndIsAllowTrue(Long userId);

    @Query("select new com.cbc.design.auth.web.dto.UserPublishDTO(u,pc) from User u left join PublishCritic pc on u.id = pc.userId and pc.isAllow = 1 order by pc.good desc,pc.id desc")
    Page<UserPublishDTO> findByOrderByGoodDesc(Pageable pageable);
}
