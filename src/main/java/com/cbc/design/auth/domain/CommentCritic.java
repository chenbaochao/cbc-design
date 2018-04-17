package com.cbc.design.auth.domain;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 记录说说的评论
 * Created by cbc on 2018/4/1.
 */
@Data
@Entity
public class CommentCritic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean isGood;

    private Long publishCriticId;

    private LocalDateTime time;

    private Long userId;

    private LocalDateTime updateDate;

    private Boolean isAllow;

    private String critic;

    @Transient
    private String nickname;

    @Transient
    private String avatar;

}
