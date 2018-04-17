package com.cbc.design.auth.service;

import com.cbc.design.auth.repositories.GoodCriticRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by cbc on 2018/4/2.
 */
@Service
@AllArgsConstructor
public class GoodCriticService {

    private final GoodCriticRepository goodCriticRepository;

    /**
     *  通过用户id 获取好评数量
     * @param userId
     * @return
     */
    public Long countGoodCountByUserId(Long userId) {

       return  goodCriticRepository.countByUserIdAndIsAllowTrue(userId);
    }
}
