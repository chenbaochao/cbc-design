package com.cbc.design.movie.parse;

import com.cbc.design.common.JsoupUtil;
import com.cbc.design.movie.crawler.TencentCrawler;
import com.cbc.design.movie.domain.Episode;
import com.cbc.design.movie.domain.Star;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.domain.VideoClass;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoParserManager implements ParserManager {

    private static final String baseUrl = "http://api.bbbbbb.me/svip/v.php/?url=";

    private static final String tencentUrl = "http://v.qq.com";

    @Override
    public Object parse(String url) {
        Document document = JsoupUtil.getDocWithPC(url);
        Elements videoTitle = document.select(".video_title");
        Elements imgElement = document.select(".mod_figure_list_sm img");
        String image = "";
        if(!imgElement.isEmpty()) {
            image = document.select(".mod_figure_list_sm img").first().attr("r-lazyload");
        }
        String title = videoTitle.text();
        String name = document.select(".player_title a").text();
/*
        String englishTitle = videoTitle.select(".alias").text();
*/
        Elements mainInfo = document.select(".wrapper_main");
        String director = "";
        String allStar = "";
        if (!mainInfo.select(".director").isEmpty()) {
            Elements directorElement = mainInfo.select(".director > a");
            if(!directorElement.isEmpty()) {
                director = mainInfo.select(".director > a").first().text();
            }
            int directorIndex = mainInfo.select(".director").text().indexOf(director);
            allStar = mainInfo.select(".director").text().substring(directorIndex + director.length());
        }
        String introduction = mainInfo.select(".summary").text();
        Elements starsInfo = mainInfo.select(".item");
        List<Star> stars = starsInfo.stream().map(e ->
                Star
                        .builder()
                        .starImage(e.select("img").attr("r-lazyload"))
                        .starName(e.select(".name").text())
                        .build()
        ).collect(Collectors.toList());
        Elements items = document.select(".mod_episode .item");
        List<Episode> episodes = items.stream().map(u ->
                Episode
                        .builder()
                        .number(u.text())
                        .url(tencentUrl+u.select("a").attr("href"))
                        .build()
        ).collect(Collectors.toList());
        String totalEpisode = document.select(".people_num").text();
        return Video
                .builder()
                .title(title)
                .name(name)
                .introduction(introduction)
                .image(image)
                .playUrl(baseUrl + url)
                .value(url)
                .allStar(allStar)
                .director(director)
                .stars(stars)
                .episodes(episodes)
                .totalEpisode(totalEpisode)
                .build();

    }


}
