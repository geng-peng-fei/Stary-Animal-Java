package com.gpf.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: WebSocketMessageDTO
 * @description:
 * @date 2023/3/7 13:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessageDTO implements Serializable {

    private static final long serialVersionUID = -6060343040263809614L;

    /**
     * 消息类型 1、会话消息 2、列表消息
     */
    private Integer status;

    /**
     * 消息内容
     */
    private Object data;

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
