package com.cbc.design.movie.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.UserRepository;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.parse.ParserManager;
import lombok.AllArgsConstructor;
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
import java.util.Optional;


@Controller
@AllArgsConstructor
public class MovieController {

    private final ParserManager parseManager;

    private final UserRepository userRepository;


    @GetMapping("/movie")
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

}


