package com.cbc.design.auth.web;

import com.cbc.design.auth.domain.GoodCritic;
import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.service.*;
import com.cbc.design.auth.web.dto.MyFriendDTO;
import com.cbc.design.common.MyUtil;
import com.cbc.design.common.RepeatSubmit;
import com.cbc.design.common.TokenProcessor;
import com.cbc.design.movie.crawler.TencentCrawler;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.redis.RedisSourceManager;
import com.cbc.design.movie.service.SearchService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cbc on 2018/3/30.
 */
@Controller
@AllArgsConstructor
public class PageController {

    private final FriendService friendService;

    private final PublishCriticService publishCriticService;

    private final CommentCriticService commentCriticService;

    private final CollectionCriticService collectionCriticService;

    private final GoodCriticService goodCriticService;

    private final RedisSourceManager redisSourceManager;

    private final static String[] TAGS = {"QQ"};

    private final SearchService searchService;

    private final TencentCrawler crawler;

    /**
     *  第一步注册
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/register",method = {RequestMethod.GET,RequestMethod.POST})
    public String register(Model model, HttpServletRequest request){
        TokenProcessor tokenProcessor = TokenProcessor.getInstance();
        String token = tokenProcessor.generateToken();
        tokenProcessor.saveToken(request,"register1_token",token);
        model.addAttribute("token",token);
        //获取注册协议
        String txt = MyUtil.getInstance().getTxt();
        model.addAttribute("txt",txt);
        return "/registered/register";
    }

    /**
     *  第二步 注册
     * @param photo
     * @param phone
     * @param code_phone
     * @param token
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/register2",method = {RequestMethod.POST,RequestMethod.GET})
    public String register(@RequestParam(required = false) String photo,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String phone,
                           @RequestParam(required = false) String code_phone,
                           @RequestParam(required = false) String token,
                           Model model,
                           HttpServletRequest request){
        HttpSession session = request.getSession();
        TokenProcessor tokenProcessor = TokenProcessor.getInstance();
        boolean isRepeatSubmit = RepeatSubmit.isRepeatSubmit(token, (String)session.getAttribute("register1_token"));
        //如果重复提交了表单，回到第一个注册页面
        if(isRepeatSubmit){
            return register(model,request);
        }else{
            tokenProcessor.deleteToken(request,"register1_token");
        }
        //
        session.setAttribute("phone",phone);
        String register2_token = tokenProcessor.generateToken();
        session.setAttribute("register2_token",register2_token);
        session.setAttribute("email",email);
        model.addAttribute("token",register2_token);
        model.addAttribute("phone",phone);
        model.addAttribute("photo",photo);
        model.addAttribute("code_phone",code_phone);
        return "/registered/register2";
    }


    /**
     *  第三个注册页面
     * @return
     */
    @GetMapping("/register3")
    public String register3(){

        return "/registered/register3";
    }

    /**
     * 首页
     * @param model
     * @return
     */
    @GetMapping({"/","/home"})
    public String home(Model model, Pageable pageable){
/*        Long userId = user.getId();
        List<MyFriendDTO> friends = friendService.getFriendByUser(userId);
        model.addAttribute("user",user);*/

        List<Video> carouselPics = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_CAROUSEL_KEY, TAGS[0]);
        List<Video> recommends = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_RECOMMEND_KEY, TAGS[0]);
        List<Video> tvHots = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_TV_KEY, TAGS[0]);
        List<Video> movies = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_MOVIE_KEY, TAGS[0]);
        List<Video> carToons = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_CARTOON_KEY, TAGS[0]);
        if(CollectionUtils.isEmpty(carouselPics)||CollectionUtils.isEmpty(tvHots)||CollectionUtils.isEmpty(movies)||CollectionUtils.isEmpty(carToons)){
            crawler.start();
            carouselPics = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_CAROUSEL_KEY, TAGS[0]);
            recommends = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_RECOMMEND_KEY, TAGS[0]);
            tvHots = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_TV_KEY, TAGS[0]);
            movies = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_MOVIE_KEY, TAGS[0]);
            carToons = redisSourceManager.getVideosByKeyAndTag(redisSourceManager.VIDEO_PREFIX_HOME_CARTOON_KEY, TAGS[0]);
        }
        model.addAttribute("carouselPics", carouselPics);
        model.addAttribute("recommends", recommends);
        model.addAttribute("tvHots", tvHots);
        model.addAttribute("movies", movies);
        model.addAttribute("carToons",carToons);
/*
        model.addAttribute("myFriends",friends);
        model.addAttribute("action",1);

        List<Long> userIds = friends.stream().map(MyFriendDTO::getFriendId).collect(Collectors.toList());
        userIds.add(user.getId());
        */
/** 获取所有用户 说说信息（包括自己） *//*

        model.addAttribute("result",publishCriticService.getUserPublish(userIds,pageable));
        //获取当前用户的评论数量
        model.addAttribute("comments", commentCriticService.countCommentCountByUserId(userId));

        //获取当前用户的  说说数量
        model.addAttribute("critics", publishCriticService.countPublishCountByUserId(userId));
        // 获取当前用户的  点赞数量
        model.addAttribute("goods", goodCriticService.countGoodCountByUserId(userId));
        // 获取当前用户的  收藏数量
        model.addAttribute("collections", collectionCriticService.countCollectionCountByUserId(userId));
*/


        return "home";
    }


    @GetMapping("/home02")
    public String home02(@AuthenticationPrincipal User user,Model model,Pageable pageable){
        Long userId = user.getId();
        List<MyFriendDTO> friends = friendService.getFriendByUser(userId);
        model.addAttribute("user",user);
        model.addAttribute("myFriends",friends);
        model.addAttribute("action",1);

        List<Long> userIds = friends.stream().map(MyFriendDTO::getFriendId).collect(Collectors.toList());
        userIds.add(user.getId());
/** 获取所有用户 说说信息（包括自己）*/

        model.addAttribute("result",publishCriticService.getUserPublish(userIds,pageable));
        //获取当前用户的评论数量
        model.addAttribute("comments", commentCriticService.countCommentCountByUserId(userId));

        //获取当前用户的  说说数量
        model.addAttribute("critics", publishCriticService.countPublishCountByUserId(userId));
        // 获取当前用户的  点赞数量
        model.addAttribute("goods", goodCriticService.countGoodCountByUserId(userId));
        // 获取当前用户的  收藏数量
        model.addAttribute("collections", collectionCriticService.countCollectionCountByUserId(userId));

        return "home02";
    }

    @GetMapping("/userinfo")
    public String userInfo(){
        return "userinfo";
    }


    @GetMapping("/allmovie")
    public String allMovie(){
        return "allmovie";
    }
}
