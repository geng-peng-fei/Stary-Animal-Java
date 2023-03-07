package com.gpf.animal.controller;

import com.gpf.animal.common.Result;
import com.gpf.animal.entity.SubComment;
import com.gpf.animal.service.SubCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gengpengfei
 * @version V1.0
 * @ClassNane: SubCommentController
 * @description:
 * @date 2022/11/12 12:27
 */
@RestController
@RequestMapping("/subComment")
public class SubCommentController {

    @Resource
    private SubCommentService subCommentService;

    /**
     * 删除子评论
     *
     * @return
     * @parme comment
     */
    @DeleteMapping("/{ids}")
    public Result deleteSubComment(@PathVariable List<Long> ids) {
        return subCommentService.deleteSubComment(ids);
    }

    /**
     * 新增评论
     *
     * @return
     * @parme comment
     */
    @PostMapping
    public Result insertSubComment(@RequestBody SubComment subComment) {
        return subCommentService.insertSubComment(subComment);
    }
}
