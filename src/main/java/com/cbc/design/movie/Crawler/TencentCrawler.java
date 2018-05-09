package com.cbc.design.movie.crawler;

import com.cbc.design.common.JsoupUtil;
import com.cbc.design.movie.domain.Video;
import com.cbc.design.movie.domain.VideoClass;
import com.cbc.design.movie.redis.RedisSourceManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 腾讯视频信息爬虫
 */
@Component
@Log4j2
@AllArgsConstructor
public class TencentCrawler {

    private static final String HOME_PAGE_PC = "https://v.qq.com/";
    private static final String HOME_PAGE_PHONE_TV = "http://v.qq.com/x/list/tv";
    private static final String HOME_PAGE_PHONE_MOVIE = "http://v.qq.com/x/list/movie";
    private static final String HOME_PAGE_PHONE_CARTOON = "http://v.qq.com/x/list/cartoon";
    private static final String HOME_PAGE_PHONE_RECOMMEND = "http://v.qq.com/x/list/variety";
    private static final String TAG = "QQ";

    private static final String MOVIE_HOME_PAGE_PC = "https://v.qq.com/movie";

    private static final String TV_HOME_PAGE_PC = "https://v.qq.com/tv";

    private static final String ZY_HOME_PAGE_PC = "https://v.qq.com/variety";

    private static final String CT_HOME_PAGE_PC = "https://v.qq.com/cartoon";


    private final RedisSourceManager redisSourceManager;

    /**
     * 每隔1小时， 爬腾讯视频官网信息
     */
    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    public void start() {
        Document pcDocument = JsoupUtil.getDocWithPC(HOME_PAGE_PC);
        Document tvDocument = JsoupUtil.getDocWithPC(HOME_PAGE_PHONE_TV);
        Document movieDocument = JsoupUtil.getDocWithPC(HOME_PAGE_PHONE_MOVIE);
        Document zongyiDocument = JsoupUtil.getDocWithPC(HOME_PAGE_PHONE_RECOMMEND);
        Document carToonDocument = JsoupUtil.getDocWithPC(HOME_PAGE_PHONE_CARTOON);

        Document movieHomeDocument = JsoupUtil.getDocWithPC(MOVIE_HOME_PAGE_PC);
        Document TVHomeDocument = JsoupUtil.getDocWithPC(TV_HOME_PAGE_PC);
        Document ZYHomeDocument = JsoupUtil.getDocWithPC(ZY_HOME_PAGE_PC);
        Document CTHomeDocument = JsoupUtil.getDocWithPC(CT_HOME_PAGE_PC);



        saveCarouselsToRedis(pcDocument);
        saveRecommendsToRedis(zongyiDocument);
        saveTVsToRedis(tvDocument);
        saveMoviesToRedis(movieDocument);
        saveCartoonsToRedis(carToonDocument);

        saveMovieCarouselsToRedis(movieHomeDocument);
        saveTVCarouselsToRedis(TVHomeDocument);
        saveZYCarouselsToRedis(ZYHomeDocument);
        saveCTCarouselsToRedis(CTHomeDocument);
        saveMovieHomeToRedis(movieDocument);
        saveTVHomeToRedis(tvDocument);
        saveZYHomeToRedis(zongyiDocument);
        saveCTHomeToRedis(carToonDocument);
    }


    /**
     * 爬腾讯视频官网-首页轮播信息
     */
    private void saveCarouselsToRedis(Document document) {

        String key = redisSourceManager.VIDEO_PREFIX_HOME_CAROUSEL_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getCarouselsFromDocument(document));
    }

    /**
     * 爬腾讯PC站-综艺
     */
    public void saveRecommendsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_HOME_RECOMMEND_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getVideosFromPhoneDocument(document));
    }

    /**
     * 爬腾讯PC站-电视剧
     */
    public void saveTVsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_HOME_TV_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getVideosFromPhoneDocument(document));
    }

    /**
     * 爬腾讯PC站-电影
     */
    public void saveMoviesToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_HOME_MOVIE_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getVideosFromPhoneDocument(document));
    }

    /**
     * 爬腾讯PC站-动漫
     */
    public void saveCartoonsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_HOME_CARTOON_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getVideosFromPhoneDocument(document));
    }


    public static List<Video> getVideosFromPhoneDocument(Document document) {
        List<Video> videos = new ArrayList<>();
        Elements elements = document.select("li.list_item a.figure");
        for (Element element : elements) {
            Video video = new Video();
            String url = element.attr("href");
            String title = element.select("img").attr("alt");
            String image = element.select("img").attr("r-lazyload");
            video.setTitle(title);
            video.setImage(image);
            video.setValue(url);
            videos.add(video);
        }
        return videos;
    }


    /**
     * 爬腾讯视频官网-电影轮播信息
     */
    public void saveMovieCarouselsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_MOVIE_CAROUSEL_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getCarouselsFromDocument(document));
    }

    /**
     * 爬腾讯视频官网-电视剧轮播信息
     */
    public void saveTVCarouselsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_TV_CAROUSEL_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getCarouselsFromDocument(document));
    }

    /**
     * 爬腾讯视频官网-综艺轮播信息
     */
    public void saveZYCarouselsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_ZY_CAROUSEL_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getCarouselsFromDocument(document));
    }

    /**
     * 爬腾讯视频官网-动漫轮播信息
     */
    public void saveCTCarouselsToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_CT_CAROUSEL_KEY + "_" + TAG;
        redisSourceManager.saveVideos(key, getCarouselsFromDocument(document));
    }


    /**
     * 爬腾讯视频官网-电影首页信息
     */
    public void saveMovieHomeToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_MOVIE_KEY + "_" + TAG;
        redisSourceManager.saveVideoClass(key, getVideoClassFromDocument(document,HOME_PAGE_PHONE_MOVIE));
    }

    /**
     * 爬腾讯视频官网-电视剧首页信息
     */
    public void saveTVHomeToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_TV_KEY + "_" + TAG;
        redisSourceManager.saveVideoClass(key, getVideoClassFromDocument(document,HOME_PAGE_PHONE_TV));
    }

    /**
     * 爬腾讯视频官网-综艺首页信息
     */
    public void saveZYHomeToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_ZY_KEY + "_" + TAG;
        redisSourceManager.saveVideoClass(key, getVideoClassFromDocument(document,HOME_PAGE_PHONE_RECOMMEND));
    }

    /**
     * 爬腾讯视频官网-电视剧首页信息
     */
    public void saveCTHomeToRedis(Document document) {
        String key = redisSourceManager.VIDEO_PREFIX_CT_KEY + "_" + TAG;
        redisSourceManager.saveVideoClass(key, getVideoClassFromDocument(document,HOME_PAGE_PHONE_CARTOON));
    }


    /**
     * 爬轮播图
     *
     * @param document
     * @return
     */
    public List<Video> getCarouselsFromDocument(Document document) {
        List<Video> carouselVideos = new ArrayList<>();
        Elements carousels = document.select("div.slider_nav a");
        for (Element carousel : carousels) {
            Video video = new Video();
            String title = carousel.select("div.txt").text();
            String image = carousel.attr("data-bgimage");
            String url = carousel.attr("href");
            video.setValue(url);
            video.setTitle(title);
            video.setImage(image);
            carouselVideos.add(video);
        }
        return carouselVideos;
    }


    /**
     * 各种类的视频
     *
     * @param document
     * @return
     */
    public List<VideoClass> getVideoClassFromDocument(Document document,String url) {
        List<VideoClass> result = new ArrayList<>();
        Elements elements = document.select("li.item a");

        for (Element element : elements) {
            VideoClass vc = new VideoClass();
            vc.setName(element.text());
            Document document2 = JsoupUtil.getDocWithPC(url+element.attr("href"));
            vc.setVideos(getVideosFromPhoneDocument(document2));
            result.add(vc);
        }
        return result;
    }



    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        Document document = JsoupUtil.getDocWithPC("https://v.qq.com/x/variety/");

        List<VideoClass> result = new ArrayList<>();
        Elements carousels = document.select("div.slider_nav a");
        for (Element carousel : carousels) {
            Video video = new Video();
            String title = carousel.select("div.txt").text();
            String image = carousel.attr("data-bgimage");
            String url = carousel.attr("href");
            video.setValue(url);
            video.setTitle(title);
            video.setImage(image);
            System.out.println(video);
        }
        System.out.println();
    }
}
