package com.gpf.animal.common;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: WebSocketServer
 * @description:
 * @date 2023/3/7 12:30
 */
@Component
@Slf4j
@ServerEndpoint("/chat/{adminName}")
public class WebSocketServer {


    /**
     * 存储连接信息
     */
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param adminName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("adminName") String adminName) {
        sessionMap.put(adminName, session);
        log.info("有新用户加入，adminName={}, 当前在线人数为：{}", adminName, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("admins", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("adminName", key);
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
        //{"users": [{"username": "zhang"},{ "username": "admin"}]}
        // 后台发送消息给所有的客户端
        sendAllMessage(JSONUtil.toJsonStr(result));
    }

    /**
     * 连接关闭调用的方法
     *
     * @param session
     * @param adminName
     */
    @OnClose
    public void onClose(Session session, @PathParam("adminName") String adminName) {
        sessionMap.remove(adminName);
        log.info("有一连接关闭，移除adminName={}的用户session, 当前在线人数为：{}", adminName, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message   客户端发送过来的消息
     * @param session
     * @param adminName
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("adminName") String adminName) {
        log.info("服务端收到用户username={}的消息:{}", adminName, message);
        JSONObject obj = JSONUtil.parseObj(message);
        // to表示发送给哪个用户，比如 admin
        String toAdminName = obj.getStr("to");
        // 发送的消息文本  hello
        String text = obj.getStr("text");
        // {"to": "admin", "text": "聊天文本"}   根据 to用户名来获取 session，再通过session发送消息文本
        Session toSession = sessionMap.get(toAdminName);
        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            // from 是 zhang
            jsonObject.set("from", adminName);
            // text 同上面的text
            jsonObject.set("text", text);
            this.sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户adminName={}，消息：{}", toAdminName, jsonObject.toString());
        } else {
            log.info("发送失败，未找到用户adminName={}的session", toAdminName);
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 服务端发送消息给客户端
     *
     * @param message
     * @param toSession
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     *
     * @param message
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

}
