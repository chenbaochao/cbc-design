package com.cbc.design.auth.web.dto;

import com.cbc.design.auth.domain.PublishCritic;
import com.cbc.design.auth.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Created by cbc on 2018/4/1.
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserPublishDTO {

    private Long userId;

    private Long publishCriticId;

    private String avatar;

    private String nickname;

    private LocalDateTime time;

    private String critic;

    private String title;

    private String picture;

    /** 缓存图片地址*/
    private String thumbnails;

    /** 收藏的评论数量 */
    private Long collectionCounts;

    private Boolean isPrivate;

    /** 说说评论数量 */
    private Long commentCounts;

    /** 好评数量 */
    private Long goodCounts;

    /** 是否点赞过 */
    private Boolean isGood;

    /** 是否收藏过 */
    private Boolean isCollection;


    public UserPublishDTO(User user,PublishCritic pc){
        this.userId = user.getId();
        this.publishCriticId = pc.getId();
        this.avatar = user.getAvatar();
        this.nickname = user.getNickname();
        this.time = pc.getTime();
        this.critic = pc.getCritic();
        this.title = pc.getTitle();
        this.picture = pc.getPicture();
        this.thumbnails = pc.getThumbnails();
        this.isPrivate = pc.getIsPrivate();
    }
}


