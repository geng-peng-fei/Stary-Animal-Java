package com.gpf.animal.dto;

import lombok.Data;

/**
 * @author gengpengfei
 */
@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String picture;
    private String status;
    private String token;
}
