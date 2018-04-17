package com.cbc.design.auth.web;

import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.service.FriendService;
import com.cbc.design.auth.web.dto.MyFriendDTO;
import com.cbc.design.common.ResponseBean;
import com.cbc.design.common.ResultUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cbc on 2018/4/1.
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    /**
     *  通过用户 获取所有好友
     * @param userId
     * @return
     */
    @GetMapping("/user/by/{userId}")
    public ResponseBean getFriendByUser(@PathVariable Long userId){
        List<MyFriendDTO> result = friendService.getFriendByUser(userId);
        return ResultUtil.success(result);
    }
}
