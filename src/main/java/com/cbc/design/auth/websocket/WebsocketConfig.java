package com.cbc.design.auth.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer {

    /**
     * 注册 stomp的端点
     * @param registry
     */
    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        //允许使用socketJs方式访问，访问点为cbc,允许跨域
        //在网页上我们可以通过这个链接 http://localhost/cbc
        //来和服务器的websocket连接
        registry.addEndpoint("/cbc").setAllowedOrigins("*").withSockJS();
    }

    /**
     *  配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅代理名称
        registry.enableSimpleBroker("/quene","/topic");
        //全局使用的消息前缀(客户端订阅路径上会体现出来)
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        // registry.setUserDestinationPrefix("/user/");

    }
}
