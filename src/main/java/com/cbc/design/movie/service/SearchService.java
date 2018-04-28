package com.cbc.design.movie.service;

import com.cbc.design.common.JsoupUtil;
import com.cbc.design.movie.domain.Episode;
import com.cbc.design.movie.domain.Star;
import com.cbc.design.movie.domain.Video;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private static final String baseUrl = "https://v.qq.com/x/search/?q=";


    public List<Video> search(String keyword) {
        Document document = JsoupUtil.getDocWithPC(baseUrl + keyword);
        Elements videos = document.select(".result_item_v");
        return videos.stream().map(video -> {
            String totalEpisode = video.select(".figure_info").text();
            String title = video.select(".result_title").text();
            String director = video.select(".info_item_even .content").text();
            String allStar = video.select(".info_item_odd .content >a").text();
            String introduction = video.select(".info_item_desc .desc_text").text();
            Elements episodeElement = video.select(".result_episode_list .item");
            String image = video.select(".result_figure >img").attr("src");
            List<Episode> episodes = episodeElement.stream().map(u ->
                    Episode
                            .builder()
                            .number(u.text())
                            .url(u.select("a").attr("href"))
                            .build()
            ).collect(Collectors.toList());
            return Video
                    .builder()
                    .title(title)
                    .allStar(allStar)
                    .introduction(introduction)
                    .director(director)
                    .episodes(episodes)
                    .totalEpisode(totalEpisode)
                    .image(image)
                    .build();
        }).collect(Collectors.toList());

    }

/*
    public static void main(String[] args) {
        Document document = JsoupUtil.getDocWithPC("https://v.qq.com/x/search/?q=好久不见");
        Elements videos = document.select(".result_item_v");
        for (Element video : videos) {
            String totalEpisode = video.select(".figure_info").text();
            String title = video.select(".result_title").text();
            String director = video.select(".info_item_even .content").text();
            String allStar = video.select(".info_item_odd .content >a").text();
            String introduction = video.select(".info_item_desc .desc_text").text();
            Elements episodeElement = video.select(".result_episode_list .item");
            String image = video.select(".result_figure >img").attr("src");
            List<Episode> episodes = episodeElement.stream().map(u ->
                    Episode
                            .builder()
                            .number(u.text())
                            .url(u.select("a").attr("href"))
                            .build()
            ).collect(Collectors.toList());
            Video
                    .builder()
                    .title(title)
                    .allStar(allStar)
                    .introduction(introduction)
                    .director(director)
                    .episodes(episodes)
                    .totalEpisode(totalEpisode)
                    .image(image)
                    .build();
        }

        System.out.println(videos.get(0).select(".result_figure >img"));
    }*/
}
