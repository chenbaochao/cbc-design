package com.cbc.design.auth.repositories;

import com.cbc.design.auth.domain.Friend;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.web.dto.MyFriendDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cbc on 2018/4/1.
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
    @Query("select new com.cbc.design.auth.web.dto.MyFriendDTO(user,friend) from User user,Friend friend where user.id = friend.friendId and friend.userId = ?1 ")
    List<MyFriendDTO> findByUserId(Long userId);
}
