package com.cbc.design.movie.parse;

/**
 *  解析管理器
 */
public interface ParserManager {



    /**
     * 从 url 中解析资源
     * @param url 目标资源地址
     * @return 资源信息
     */
    Object parse(String url);
}
