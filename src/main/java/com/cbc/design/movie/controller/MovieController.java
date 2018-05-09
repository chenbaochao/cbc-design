package com.cbc.design.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.UserRepository;
import com.cbc.design.movie.crawler.TencentCrawler;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.domain.VideoClass;
import com.cbc.design.movie.parse.ParserManager;
import com.cbc.design.movie.redis.RedisSourceManager;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
public class MovieController {

    private final ParserManager parseManager;

    private final RedisSourceManager redisSourceManager;

    private final UserRepository userRepository;

    private final static String[] TAGS = {"QQ"};

    private final TencentCrawler crawler;


    @GetMapping("/play")
    public String play(@RequestParam String url, Model model, @AuthenticationPrincipal User user) {
        Video video = (Video) parseManager.parse(url);
        model.addAttribute("video", video);
        model.addAttribute("user",user);
        return "video-play";
    }

    @MessageMapping("/video/{id}")
  //  @SendTo("/topic/video/{url}")
    public Object message(@DestinationVariable String id, Principal principal, String message){
        User user = userRepository.findByUsername(principal.getName()).get();
        JSONObject json = new JSONObject();
        json.put("content", message);
        json.put("avatar",user.getAvatar());
        return json;
    }

    @GetMapping("/movie")
    public String movie(Model model){
        List<Video> movieCarousels = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_MOVIE_CAROUSEL_KEY, TAGS[0]);
        List<VideoClass> videoClasses = redisSourceManager.getVideoClassByKeyAndTag(redisSourceManager.VIDEO_PREFIX_MOVIE_KEY, TAGS[0]);
        if(CollectionUtils.isEmpty(movieCarousels)||CollectionUtils.isEmpty(videoClasses)){
            crawler.start();
            movieCarousels = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_MOVIE_CAROUSEL_KEY, TAGS[0]);
            videoClasses = redisSourceManager.getVideoClassByKeyAndTag(redisSourceManager.VIDEO_PREFIX_MOVIE_KEY, TAGS[0]);
        }
        model.addAttribute("movieCarousels",movieCarousels);
        model.addAttribute("videoClasses",videoClasses);
        return "movie";
    }

}


