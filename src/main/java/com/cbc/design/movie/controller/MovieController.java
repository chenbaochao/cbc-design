package com.cbc.design.movie.controller;

import com.cbc.design.auth.domain.User;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.parse.ParserManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class MovieController {

    private final ParserManager parseManager;


    @GetMapping("/movie")
    public String play(@RequestParam String url, Model model, @AuthenticationPrincipal User user) {
        Video video = (Video) parseManager.parse(url);
        model.addAttribute("video", video);
        model.addAttribute("user",user);
        return "video-play";
    }

}


