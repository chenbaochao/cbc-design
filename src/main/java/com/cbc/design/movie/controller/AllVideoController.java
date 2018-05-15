package com.cbc.design.movie.controller;

import com.cbc.design.movie.domain.Nav;
import com.cbc.design.movie.domain.Title;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.domain.VideoSort;
import com.cbc.design.movie.service.AllVideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class AllVideoController {

    private final AllVideoService allVideoService;

    @GetMapping("/allmovie")
    public String allmovie(@RequestParam(defaultValue = "http://v.qq.com/x/list/movie") String title,
                           @RequestParam(defaultValue = "0")String offset,
                           @RequestParam(required = false)String subtype,
                           @RequestParam(required = false)String iarea,
                           @RequestParam(required = false)String format,
                           @RequestParam(required = false)String iyear,
                           @RequestParam(required = false)String pay,
                           @RequestParam(required = false)String ipay,
                           @RequestParam(required = false)String exclusive,
                           @RequestParam(required = false)String itype,
                           @RequestParam(required = false)String plot_aspect,
                           @RequestParam(required = false)String feature,
                           @RequestParam(required = false)String theatre,
                           @RequestParam(required = false)String sort,
                           @RequestParam(required = false)String language,
                           Model model) {
        //获取标题
        List<Title> titles = allVideoService.getTitles();
        model.addAttribute("titles",titles);
        List<VideoSort> videoSorts = allVideoService.getVideoSorts(title);
        model.addAttribute("videoSorts",videoSorts);
        List<Nav> navs = allVideoService.getNavs(title);
        model.addAttribute("navs",navs);
        List<Video> videos = allVideoService.getVideos(title,offset,subtype,iarea,format,iyear,pay,ipay,exclusive,itype,plot_aspect,feature,theatre,sort,language);
        model.addAttribute("videos",videos);
        return "allmovie";

    }

}
