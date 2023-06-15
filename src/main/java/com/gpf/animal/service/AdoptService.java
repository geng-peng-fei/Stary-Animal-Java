package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Adopt;

import java.util.List;

/**
 * (Adopt)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface AdoptService extends IService<Adopt> {
    /**
     * 新增领养申请
     *
     * @param adopt
     * @return
     */
    Result insertAdopt(Adopt adopt);

    /**
     * 修改领养申请
     *
     * @param adopt
     * @return
     */
    Result updateAdopt(Adopt adopt);

    /**
     * 删除领养申请
     *
     * @param id
     * @return
     */
    Result deleteAdopt(Long id);

    /**
     * 查询领养申请
     *
     * @param id
     */
    Result getAdopt(Long id);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    Result updateStatus(int status, int id);

    /**
     * 查询领养申请列表
     */
    Result getAdoptPage(int page, int pageSize);


    /**
     * 获取echart数据
     */
    Result getAdoptEchartData(String dateConditions);
}


