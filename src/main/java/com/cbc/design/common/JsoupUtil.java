package com.cbc.design.common;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Jsoup 工具类
 */
@Log4j2
public class JsoupUtil {
    private static final String UA_PHONE = "Mozilla/5.0 (Linux; Android 4.3; Nexus 10 Build/JSS15Q) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Safari/537.36";
    private static final String UA_PC = "    Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36";
    private static final String UA_PAD = "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
    private static final int TIME_OUT = 10 * 1000;


    public static Document getDocWithPC(String url) {
        try {
            return Jsoup.connect(url).userAgent(UA_PC).timeout(TIME_OUT).ignoreContentType(true).get();
        } catch (IOException e) {
            log.error("网址请求失败：" + url);
            throw new RuntimeException("网址请求失败：" + url);
        }
    }
}