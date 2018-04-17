package com.cbc.design.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *   记录用户发布的说说
 * Created by cbc on 2018/4/1.
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PublishCritic implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long good;

    private Boolean isPrivate;

    private String picture;

    private LocalDateTime time;

    private String title;

    @NotNull
    private Long userId;

    private LocalDateTime updateDate;

    private Boolean isAllow;

    /** 评论  */
    @NotBlank
    private String critic;

    /** 缓存图片地址 */
    private String thumbnails;


}
