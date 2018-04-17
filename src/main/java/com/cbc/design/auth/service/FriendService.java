package com.cbc.design.auth.service;

import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.FriendRepository;
import com.cbc.design.auth.web.dto.MyFriendDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cbc on 2018/4/1.
 */
@Service
@AllArgsConstructor
@Transactional
public class FriendService {

    private final FriendRepository friendRepository;

    /**
     *  通过用户获取 所有好友
     * @param userId
     * @return
     */
    public List<MyFriendDTO> getFriendByUser(Long userId) {
        List<MyFriendDTO> friends = friendRepository.findByUserId(userId);
        return friends;
    }
}
