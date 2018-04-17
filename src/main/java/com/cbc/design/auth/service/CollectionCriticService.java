package com.cbc.design.auth.service;

import com.cbc.design.auth.repositories.CollectionCriticRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by cbc on 2018/4/2.
 */
@Service
@AllArgsConstructor
public class CollectionCriticService {

    private final CollectionCriticRepository collectionCriticRepository;

    /**
     *   通过用户id获取收藏的 数量
     * @param userId
     * @return
     */
    public Long countCollectionCountByUserId(Long userId) {

      return  collectionCriticRepository.countByUserIdAndIsAllowTrue(userId);
    }
}
