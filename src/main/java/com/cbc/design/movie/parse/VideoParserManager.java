package com.cbc.design.movie.parse;

import com.cbc.design.common.JsoupUtil;
import com.cbc.design.movie.domain.Star;
import com.cbc.design.movie.domain.Video;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoParserManager implements ParserManager{

    private static final String baseUrl = "http://api.bbbbbb.me/svip/v.php?url=";

    @Override
    public Object parse(String url) {
        Document document = JsoupUtil.getDocWithPC(url);
        Elements videoTitle = document.select(".video_title");
        String image = document.select(".mod_figure_list_sm img").first().attr("r-lazyload");
        String title = videoTitle.text().split(" ")[0];
        String englishTitle = videoTitle.select(".alias").text();
        Elements mainInfo = document.select(".wrapper_main");
        String director = mainInfo.select(".director > a").first().text();
        String allStar = mainInfo.select(".director").text().split("演员:")[1].trim();
        String introduction = mainInfo.select(".summary").text();
        Elements starsInfo = mainInfo.select(".item");
        List<Star> stars = starsInfo.stream().map(e ->
                Star
                        .builder()
                        .starImage(e.select("img").attr("r-lazyload"))
                        .starName(e.select(".name").text())
                        .build()
        ).collect(Collectors.toList());
        return Video
                    .builder()
                    .title(title)
                    .englishTitle(englishTitle)
                    .introduction(introduction)
                    .image(image)
                    .playUrl(baseUrl+url)
                    .value(url)
                    .allStar(allStar)
                    .director(director)
                    .stars(stars)
                    .build();

    }
}
