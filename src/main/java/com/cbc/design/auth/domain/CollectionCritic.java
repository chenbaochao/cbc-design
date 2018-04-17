package com.cbc.design.auth.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  记录用户收藏的信息
 * Created by cbc on 2018/4/1.
 */
@Data
@Entity
public class CollectionCritic  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long publishCriticId;

    private LocalDateTime time;

    private Long userId;

    private LocalDateTime updateDate;

    private Boolean isAllow;
}