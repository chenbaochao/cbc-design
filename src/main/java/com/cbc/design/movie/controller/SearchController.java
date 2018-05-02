package com.cbc.design.movie.controller;

import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword, Model model) {
        List<Video> videos = new ArrayList<>();
        if (StringUtils.isNotBlank(keyword)) {
            videos = searchService.search(keyword);
        }
        model.addAttribute("videos", videos);
        return "search";
    }


}
