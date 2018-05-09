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
public class CartoonController {


    private final RedisSourceManager redisSourceManager;

    private final TencentCrawler crawler;

    private final static String[] TAGS = {"QQ"};

    @GetMapping("/cartoon")
    public String tv(Model model){
        List<Video> ctCarousels = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_CT_CAROUSEL_KEY, TAGS[0]);
        List<VideoClass> videoClasses = redisSourceManager.getVideoClassByKeyAndTag(redisSourceManager.VIDEO_PREFIX_CT_KEY, TAGS[0]);
        if(CollectionUtils.isEmpty(ctCarousels)||CollectionUtils.isEmpty(videoClasses)){
            crawler.start();
            ctCarousels = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_CT_CAROUSEL_KEY, TAGS[0]);
            videoClasses = redisSourceManager.getVideoClassByKeyAndTag(redisSourceManager.VIDEO_PREFIX_CT_KEY, TAGS[0]);
        }
        model.addAttribute("ctCarousels",ctCarousels);
        model.addAttribute("videoClasses",videoClasses);
        return "cartoon";
    }

}
