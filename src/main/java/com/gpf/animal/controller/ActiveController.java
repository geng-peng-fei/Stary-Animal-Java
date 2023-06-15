package com.gpf.animal.controller;

import com.gpf.animal.common.PageVO;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.RequestParams;
import com.gpf.animal.entity.Active;
import com.gpf.animal.service.ActiveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * (Active)表控制层
 *
 * @author makejava
 * @since 2022-11-10 09:14:02
 */
@RestController
@RequestMapping("active")
public class ActiveController {
    /**
     * 服务对象
     */
    @Resource
    private ActiveService activeService;

    /**
     * 新增活动
     */
    @PostMapping
    public Result insertActive(@RequestBody Active active) {
        return activeService.insertActive(active);
    }

    /**
     * 删除活动
     */
    @DeleteMapping
    public Result deleteActives(@RequestParam Long id) {
        return activeService.deleteActives(id);
    }

    /**
     * 修改活动
     *
     * @param active
     */
    @PutMapping
    public Result updateActive(@RequestBody Active active) {
        return activeService.updateActive(active);
    }

    /**
     * 查询活动
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Result getActive(@PathVariable Long id) {
        return activeService.getActive(id);
    }


    /**
     * 修改状态
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable int status, @RequestParam Long id) {
        return activeService.updateStatus(status, id);
    }

    /**
     * 查询活动列表
     *
     * @return
     */
    @PostMapping("/page")
    public Result getActivePage(@RequestBody PageVO pageVO) {
        Integer page = pageVO.getPage();
        Integer pageSize = pageVO.getPageSize();
        String name = pageVO.getName();
        return activeService.getActivePage(page, pageSize, name);
    }

    /**
     * 前端获得活动列表
     *
     * @param requestParams
     * @return
     */
    @PostMapping("/list")
    public Result getActiveList(@RequestBody RequestParams requestParams) {
        return activeService.getActiveList(requestParams);
    }

    /**
     * 前端主页获得活动列表
     *
     * @return
     */
    @PostMapping("/listWu")
    public Result getActiveList() {
        return activeService.getActiveList();
    }

    /**
     * 用户加入活动
     *
     * @param activeId
     * @param userId
     * @return
     */
    @PostMapping("/join")
    public Result join(int activeId, int userId) {
        return activeService.joinActive(activeId, userId);
    }

    /**
     * echart数据
     * @return
     */
    @GetMapping("/echart")
    public Result getActiveEchartData(){
        return activeService.getActiveEchartData();
    }

}



