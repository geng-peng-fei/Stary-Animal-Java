package com.gpf.animal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: RequestParams
 * @description:
 * @date 2023/1/7 14:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestParams {

    private String key;
    private Integer page;
    private Integer pageSize;
    private String sortBy;
    private String petVarietiesName;
    private String varietiesName;

}
