package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.dto.SubCommentDTO;
import com.gpf.animal.entity.SubComment;

import java.util.List;

/**
 * @author gengpengfei
 * @version V1.0
 * @interfaceNane: SubCommentService
 * @description:
 * @date 2022/11/12 12:28
 */
public interface SubCommentService extends IService<SubComment> {
    /**
     * 新增评论
     *
     * @return
     * @parme comment
     */
    Result insertSubComment(SubComment subComment);

    /**
     * 删除评论
     *
     * @param ids
     * @return
     * @parme subComment
     */
    Result deleteSubComment(List<Long> ids);

    List<SubCommentDTO> listSubComments(Long id);
}
