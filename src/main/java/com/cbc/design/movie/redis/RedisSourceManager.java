package com.cbc.design.movie.redis;

import com.alibaba.fastjson.JSONObject;
import com.cbc.design.movie.domain.Video;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class RedisSourceManager {

    public final String VIDEO_PREFIX_HOME_CAROUSEL_KEY = "HOME_VIDEO_CAROUSEL";
    public final String VIDEO_PREFIX_HOME_RECOMMEND_KEY = "HOME_VIDEO_RECOMMEND";
    public final String VIDEO_PREFIX_HOME_TV_KEY = "HOME_VIDEO_TV";
    public final String VIDEO_PREFIX_HOME_MOVIE_KEY = "HOME_VIDEO_MOVIE";
    public final String VIDEO_PREFIX_HOME_CARTOON_KEY = "HOME_VIDEO_CARTOON";
    public final String VIDEOS_KEY = "VIDEOS";

    private final StringRedisTemplate stringRedisTemplate;

    /**
     *  保存视频信息到 Redis
     */
    public void saveVideos(String key, List<Video> videos){
        String value = JSONObject.toJSONString(videos);
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     *  得到视频信息
     */
    public List<Video> getVideosByKeyAndTag(String key, String tag){
        key = key + "_" + tag;
        String cacheValue = stringRedisTemplate.opsForValue().get(key);
        return JSONObject.parseArray(cacheValue, Video.class);
    }
}
