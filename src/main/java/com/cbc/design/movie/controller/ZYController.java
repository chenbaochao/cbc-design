package com.cbc.design.movie.controller;

import com.cbc.design.movie.crawler.TencentCrawler;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.domain.VideoClass;
import com.cbc.design.movie.redis.RedisSourceManager;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ZYController {

    private final RedisSourceManager redisSourceManager;

    private final TencentCrawler crawler;

    private final static String[] TAGS = {"QQ"};

    @GetMapping("/recommend")
    public String tv(Model model){
        List<Video> zyCarousels = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_ZY_CAROUSEL_KEY, TAGS[0]);
        List<VideoClass> videoClasses = redisSourceManager.getVideoClassByKeyAndTag(redisSourceManager.VIDEO_PREFIX_ZY_KEY, TAGS[0]);
        if(CollectionUtils.isEmpty(zyCarousels)||CollectionUtils.isEmpty(videoClasses)){
            crawler.start();
            zyCarousels = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_ZY_CAROUSEL_KEY, TAGS[0]);
            videoClasses = redisSourceManager.getVideoClassByKeyAndTag(redisSourceManager.VIDEO_PREFIX_ZY_KEY, TAGS[0]);
        }
        model.addAttribute("zyCarousels",zyCarousels);
        model.addAttribute("videoClasses",videoClasses);
        return "recommend";
    }

}
