package com.cbc.design.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 视频
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    /* 视频名称 */
    private String title;

    /* 视频图片 */
    private String image;

    /* 视频源地址 */
    private String value;

    /* 其他信息 */
    private String other;

    private String playUrl;

    private String name;

    private String introduction;

    private List<Star> stars;

    private String director;

    private String allStar;

    private String totalEpisode;

    private List<Episode> episodes;

    private String englishTitle;
}
