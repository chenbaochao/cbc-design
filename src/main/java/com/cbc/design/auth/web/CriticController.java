package com.cbc.design.auth.web;

import com.cbc.design.auth.domain.CommentCritic;
import com.cbc.design.auth.domain.PublishCritic;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.service.CommentCriticService;
import com.cbc.design.auth.service.PublishCriticService;
import com.cbc.design.auth.web.dto.UserPublishDTO;
import com.cbc.design.common.ResponseBean;
import com.cbc.design.common.ResultUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cbc on 2018/4/2.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/critic")
public class CriticController {

    private final PublishCriticService publicCriticService;

    private final CommentCriticService commentCriticService;

    @PostMapping("/publish/critic")
    public ResponseBean addPublishCritic(PublishCritic critic,
                                         HttpServletRequest req,
                                         @RequestParam(value = "file_upload", required = false) MultipartFile file) {
        publicCriticService.addPublishCritic(critic, file, req);
        return ResultUtil.success();
    }

    /**
     * 向下滚动翻页说说
     *
     * @param user
     * @param pageable
     * @return
     */
    @GetMapping("/getUP")
    public ResponseBean getPublishCritic(@AuthenticationPrincipal User user, Pageable pageable) {
        List<UserPublishDTO> result = publicCriticService.getUserPublish(user, pageable);
        return ResultUtil.success(result);
    }

    /**
     * 通过用户id查询用户发表的说说
     *
     * @param userId
     * @param pageable
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseBean getPublishCriticByUid(@PathVariable Long userId, Pageable pageable) {
        List<UserPublishDTO> result = publicCriticService.getUserPublishByuserId(userId, pageable);
        return ResultUtil.success(result);
    }

    /**
     * 获取热门说说
     *
     * @param pageable
     * @return
     */
    @GetMapping("/hot/critic")
    public ResponseBean getHotCritic(Pageable pageable) {
        List<UserPublishDTO> result = publicCriticService.getHotCritic(pageable);
        return ResultUtil.success(result);
    }


    //通过影评id获取对应的评论
    @GetMapping("/get/comment/{publishCriticId}")
    public ResponseBean getCommentByPid(@PathVariable Long publishCriticId, Pageable pageable) {
        List<CommentCritic> commentCritics = commentCriticService.findByPid(publishCriticId, pageable);
        return ResultUtil.success(commentCritics);
    }



}
