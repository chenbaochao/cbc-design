package com.cbc.design.auth.service;

import com.cbc.design.auth.domain.CommentCritic;
import com.cbc.design.auth.repositories.CommentCriticRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cbc on 2018/4/2.
 */
@Service
@AllArgsConstructor
public class CommentCriticService {

    private final CommentCriticRepository commentCriticRepository;


    /**
     *   通过用户id获取评论数
     * @param userId
     * @return
     */
    public Long  countCommentCountByUserId(Long userId){
        return commentCriticRepository.countByUserIdAndIsAllowTrue(userId);
    }

    public List<CommentCritic> findByPid(Long pid, Pageable pageable){
        return commentCriticRepository.findByPublishCriticId(pid,pageable).getContent();
    }
}
