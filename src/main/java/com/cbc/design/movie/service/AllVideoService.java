package com.cbc.design.movie.service;

import com.cbc.design.common.JsoupUtil;
import com.cbc.design.movie.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllVideoService {

    private static final String qq = "http://v.qq.com/x/list/movie";


    public List<Title> getTitles() {
        Document document = JsoupUtil.getDocWithPC(qq);
        Elements elements = document.select("div.filter_list a");
        return elements.stream().map(a ->
                Title
                        .builder()
                        .name(a.text())
                        .url("?title=" + a.attr("href"))
                        .build()
        ).collect(Collectors.toList());
    }

    public List<VideoSort> getVideoSorts(String title) {
        Document document = JsoupUtil.getDocWithPC(title);
        Elements elements = document.select("ul.filter_tabs a");
        return elements.stream().map(a ->
                VideoSort
                        .builder()
                        .name(a.text())
                        .url(a.attr("href"))
                        .build()
        ).collect(Collectors.toList());
    }

    public List<Nav> getNavs(String title) {
        Document document = JsoupUtil.getDocWithPC(title);
        Elements elements = document.select("div.filter_line");
        List<Nav> navs = elements.stream().map(u -> {
            String labelName = u.select("span").text();
            Elements aElements = u.select("a");
            List<VideoType> videoTypes = aElements.stream().map(a ->
                    VideoType
                            .builder()
                            .name(a.text())
                            .url(a.attr("href"))
                            .build()
            ).collect(Collectors.toList());
            return new Nav(labelName, videoTypes);
        }).collect(Collectors.toList());
        return navs;
    }

    public List<Video> getVideos(String title, String offset, String subtype, String iarea, String format, String iyear, String pay, String ipay, String exclusive, String itype, String plot_aspect, String feature, String theatre, String sort, String language) {
        String from = title + "?offset=" + offset + "&subtype=" + subtype
                + "&iarea=" + iarea + "&format=" + format + "&iyear=" + iyear
                + "&pay=" + pay + "&ipay=" + ipay + "&exclusive=" + exclusive
                + "&itype=" + itype + "&plot_aspect=" + plot_aspect
                + "&feature=" + feature + "&language=" + language;
        if (StringUtils.isNotBlank(theatre)) {
            from = from + "&theatre=" + theatre;
        }
        if (StringUtils.isNotBlank(sort)) {
            from = from + "&sort="+ sort;
        }
        Document document = JsoupUtil.getDocWithPC(from);
        List<Video> videos = new ArrayList<>();
        Elements elements = document.select("li.list_item a.figure");
        for (Element element : elements) {
            Video video = new Video();
            String url = element.attr("href");
            String vtitle = element.select("img").attr("alt");
            String image = element.select("img").attr("r-lazyload");
            video.setTitle(vtitle);
            video.setImage(image);
            video.setValue(url);
            videos.add(video);
        }
        return videos;
    }


}
