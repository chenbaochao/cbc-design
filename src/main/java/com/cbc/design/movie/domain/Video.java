package com.cbc.design.movie.domain;

import lombok.Data;

/**
 * 视频
 */
@Data
public class Video {

    /* 视频名称 */
    private String title;

    /* 视频图片 */
    private String image;

    /* 视频源地址 */
    private String value;

    /* 其他信息 */
    private String other;

}
