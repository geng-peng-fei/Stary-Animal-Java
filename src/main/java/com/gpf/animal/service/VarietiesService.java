package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.PetVarieties;
import com.gpf.animal.entity.Varieties;

/**
 * (Varieties)表服务接口
 *
 * @author gpf
 * @since 2022-11-10 09:14:03
 */
public interface VarietiesService extends IService<Varieties> {

    /**
     * 查询宠物类型分类列表
     *
     * @return
     */
    Result varietiesList();

    /**
     * 获得类型列表(带品种)
     *
     * @param varieties
     * @return
     */
    Result getVarietiesMenu(Varieties varieties);

    /**
     * 查询宠物类型分类列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    Result varietiesPage(int page, int pageSize);

    /**
     * 编辑宠物类型分类
     *
     * @param varieties
     * @return
     */
    Result edit(Varieties varieties);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    Result deleteVarieties(Long id);

    /**
     * 添加分类
     *
     * @param varieties
     * @return
     */
    Result insertVarieties(Varieties varieties);

    /**
     * 前端获取过滤项
     *
     * @param requestParams
     * @return
     */
    Result getVarietiesFilter(RequestParams requestParams);
}

