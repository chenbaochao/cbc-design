package com.cbc.design.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 视频 类别
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoClass {

    private String name;

    private List<Video> videos;


}
