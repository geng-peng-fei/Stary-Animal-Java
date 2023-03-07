package com.gpf.animal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gpf.animal.common.Result;
import com.gpf.animal.entity.Blog;

/**
 * (Blog)表服务接口
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
public interface BlogService extends IService<Blog> {

    Result queryHotBlog(Integer current);

    Result queryBlogById(Long id);

    Result likeBlog(Long id);

    Result queryBlogLikes(Long id);

    Result queryBlogByUserId(Long id, Integer current);

    Result saveblog(Blog blog);

    Result queryBlogOfFollow(Long max, Integer offset);
}

