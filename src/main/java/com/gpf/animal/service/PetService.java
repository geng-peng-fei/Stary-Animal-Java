package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.Pet;

import java.util.List;

/**
 * (Pet)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface PetService extends IService<Pet> {

    /**
     * 查询宠物列表
     */
    Result getPetPage(Integer page, Integer pageSize, String nickName);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    Result updateStatus(int status, Long id);

    /**
     * 删除宠物（单个或批量）
     */
    Result deletePet(Long id);

    /**
     * 修改宠物
     *
     * @param pet
     */
    Result updatePet(Pet pet);

    /**
     * 查询宠物
     *
     * @param id
     */
    Result getPet(Long id);

    /**
     * 添加宠物
     *
     * @param pet
     */
    Result savePet(Pet pet);

    /**
     * 前端获得宠物列表
     *
     * @param requestParams
     * @return
     */
    Result getPetList(RequestParams requestParams);

    /**
     * 前端主页获得宠物列表
     *
     * @return
     */
    Result getPetList();

}

