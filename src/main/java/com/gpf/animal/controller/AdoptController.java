package com.gpf.animal.controller;

import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.common.StatusVo;
import com.gpf.animal.entity.Adopt;
import com.gpf.animal.service.AdoptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * (Adopt)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@RestController
@RequestMapping("adopt")
public class AdoptController {
    /**
     * 服务对象
     */
    @Resource
    private AdoptService adoptService;

    /**
     * 新增领养申请
     */
    @PostMapping
    public Result insertAdopt(@RequestBody Adopt adopt) {
        return adoptService.insertAdopt(adopt);
    }

    /**
     * 删除领养申请
     */
    @DeleteMapping("/{id}")
    public Result deleteAdopt(@PathVariable Long id) {
        return adoptService.deleteAdopt(id);
    }

    /**
     * 修改领养申请
     *
     * @param adopt
     */
    @PutMapping
    public Result updateAdopt(@RequestBody Adopt adopt) {
        return adoptService.updateAdopt(adopt);
    }

    /**
     * 查询领养申请
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Result getAdopt(@PathVariable Long id) {
        return adoptService.getAdopt(id);
    }


    /**
     * 修改状态
     *
     * @return
     */
    @PostMapping("/status")
    public Result updateStatus(@RequestBody StatusVo statusVo) {
        int status = statusVo.getStatus();
        int id = statusVo.getId();
        return adoptService.updateStatus(status, id);
    }

    /**
     * 查询领养申请列表
     */
    @PostMapping("/page")
    public Result getAdoptPage(@RequestBody PageVO pageVO) {
        Integer page = pageVO.getPage();
        Integer pageSize = pageVO.getPageSize();
        String name = pageVO.getName();
        return adoptService.getAdoptPage(page, pageSize);
    }

    /**
     * 获取echart数据
     */
    @GetMapping("/echart/{dateConditions}")
    public Result getAdoptEchartData(@PathVariable String dateConditions){
        return adoptService.getAdoptEchartData(dateConditions);
    }
}

