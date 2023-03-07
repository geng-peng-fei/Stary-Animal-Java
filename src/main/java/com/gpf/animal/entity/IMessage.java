package com.gpf.animal.entity;

import lombok.Data;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: IMessage
 * @description:
 * @date 2023/3/7 12:28
 */
@Data
public class IMessage {

    String from;
    String to;
    String message;
}