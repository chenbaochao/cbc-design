package com.cbc.design.auth.web.dto;

import com.cbc.design.auth.domain.Friend;
import com.cbc.design.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cbc on 2018/4/2.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyFriendDTO {

    private Long id;

    private Long friendId;

    private Long userId;

    private String avatar;

    private String nickname;

    public MyFriendDTO(User user,Friend friend){
        this.id = friend.getId();
        this.friendId = friend.getFriendId();
        this.userId = friend.getUserId();
        this.avatar = user.getAvatar();
        this.nickname = user.getNickname();
    }
}
