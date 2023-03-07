//package com.gpf.animal.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.gpf.animal.common.Result;
//import com.gpf.animal.dao.BlogDao;
//import com.gpf.animal.dto.ScrollResult;
//import com.gpf.animal.dto.UserDTO;
//import com.gpf.animal.entity.Blog;
//import com.gpf.animal.entity.Focus;
//import com.gpf.animal.entity.User;
//import com.gpf.animal.service.BlogService;
//import com.gpf.animal.service.FocusService;
//import com.gpf.animal.service.UserService;
//import com.gpf.animal.util.UserHolder;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * (Blog)表服务实现类
// *
// * @author makejava
// * @since 2022-11-10 09:14:03
// */
//@Service("blogService")
//public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {
//
//    @Resource
//    private UserService userService;
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//    @Resource
//    private FocusService focusService;
//
//    /**
//     * @description: 查询博客列表
//     * @author gengpengfei
//     * @date 2022/11/22 15:16
//     */
//    @Override
//    public Result queryHotBlog(Integer current) {
//        // 根据用户查询
//        Page<Blog> page = query()
//                .orderByDesc("liked")
//                .page(new Page<>(current, 10));
//        // 获取当前页数据
//        List<Blog> records = page.getRecords();
//
//        records.forEach(blog -> {
//            // 查询博客用户信息
//            this.queryBlogUser(blog);
//            //  查询是否点赞
//            this.isBlogLiked(blog);
//        });
//        //  返回当前页数据
//        return Result.ok(records);
//    }
//
//    /**
//     * @description: 根据id查询博客
//     * @author gengpengfei
//     * @date 2022/10/22 15:16
//     */
//    @Override
//    public Result queryBlogById(Long id) {
//        //1.    查询blog
//        Blog blog = getById(id);
//        //2.    查询blog的用户
//        queryBlogUser(blog);
//        //3.  查询是否被点赞
//        isBlogLiked(blog);
//        //4.    返回带用户信息的blog
//        return Result.ok(blog);
//    }
//
//    /**
//     * @description: 判断当前用户是否对博客点过赞
//     * @author gengpengfei
//     * @date 2022/10/22 15:47
//     */
//    private void isBlogLiked(Blog blog) {
//        //1.    获取登录用户
//        UserDTO user = UserHolder.getUser();
//        //  判断用户是否登陆
//        if (user == null) {
//            //  未登陆  返回
//            return;
//        }
//        Long userId = user.getId();
//        //2.    判断当前登陆用户是否点过赞
//        Long blogId = Long.valueOf(blog.getId());
//        String key = "blog:liked:" + blogId;
//        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
//        //  score!=null  说明点过赞
//        blog.setIsLike(score != null);
//    }
//
//    /**
//     * @description: 点赞功能
//     * @author gengpengfei
//     * @date 2022/10/22 15:25
//     * @version 1.0
//     */
//    @Override
//    public Result likeBlog(Long id) {
//        //1.    获取登录用户
//        Long userId = UserHolder.getUser().getId();
//        //Long userId = 1L;
//        //2.    判断当前登陆用户是否点过赞
//        String key = "blog:liked:" + id;
//        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
//        if (score == null) {
//            //3.    如果未点赞 可以点赞
//            //3.1   数据库点赞数+1
//            boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();
//            //3.2   保存用户到Redis的Set集合
//            if (isSuccess) {
//                stringRedisTemplate.opsForZSet().add(key, userId.toString(), System.currentTimeMillis());
//            }
//        } else {
//            //4.    已点赞  取消点赞
//            //4.1   数据库点赞数-1
//            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();
//            //4.2   把用户从Redis的Set集合中移除
//            if (isSuccess) {
//                stringRedisTemplate.opsForZSet().remove(key, userId.toString());
//            }
//        }
//        return Result.ok();
//    }
//
//    /**
//     * @description: 查询排行榜前几名的用户
//     * @author gengpengfei
//     * @date 2022/10/22 16:31
//     */
//    @Override
//    public Result queryBlogLikes(Long id) {
//        //1.    查询排行榜前5名的用户
//        Set<String> top5 = stringRedisTemplate.opsForZSet().range("blog:liked:" + id, 0, 4);
//        //      判断排行榜是否为空
//        if (top5 == null || top5.isEmpty()) {
//            //  为空  返回空列表
//            return Result.ok(Collections.emptyList());
//        }
//        //2.  不为空  获取用户id
//        List<Long> ids = top5.stream().map(Long::valueOf).collect(Collectors.toList());
//        //3.    查询对应用户
//        String idStr = StrUtil.join(",", ids);
//        List<UserDTO> userDTOS = userService.query()
//                .in("id", id).last("ORDER BY FIELD(id," + idStr + ") ").list()
//                .stream()
//                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
//                .collect(Collectors.toList());
//        //4.    返回用户数据
//        return Result.ok(userDTOS);
//    }
//
//    /**
//     * @description: 查询用户的所有博客
//     * @author gengpengfei
//     * @date 2022/10/23 08:09
//     */
//    @Override
//    public Result queryBlogByUserId(Long id, Integer current) {
//        //  查询用户的所有博客 并 分页
//        LambdaQueryWrapper<Blog> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Blog::getUserId, id);
//        Page<Blog> page = page(new Page<Blog>(), wrapper);
//        //  获取当前页数据
//        List<Blog> records = page.getRecords();
//        return Result.ok(records);
//    }
//
//    /**
//     * @description: 保存博客 并 推送给所有的关注者
//     * @author gengpengfei
//     * @date 2022/10/23 11:05
//     */
//    @Override
//    public Result saveblog(Blog blog) {
//        // 获取登录用户
//        UserDTO user = UserHolder.getUser();
//        Long userId = user.getId();
//        blog.setUserId(userId);
//        // 保存探店博文
//        boolean isSuccess = save(blog);
//        //  判断是否保存成功
//        if (!isSuccess) {
//            //  未成功  返回错误信息
//            return Result.fail("添加博客失败！");
//        }
//        //  成功  获取当前用户的所有关注者
//        LambdaQueryWrapper<Focus> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Focus::getFocusUserId, userId);
//        List<Focus> focusList = focusService.list(wrapper);
//        //List<Focus> focusList = focusService.query().eq("follow_user_id", userId).list();
//        //  遍历每一个关注者  向其发送博客
//        for (Focus focus : focusList) {
//            //  获得关注者的id
//            Long followUserId = focus.getFocusUserId();
//            String key = "feed:" + followUserId;
//            //  推送博客  key  blogId  当前时间戳
//            stringRedisTemplate.opsForZSet().add(key, blog.getId().toString(), System.currentTimeMillis());
//        }
//        // 返回id
//        return Result.ok(blog.getId());
//    }
//
//    /**
//     * 查询博客的
//     */
//    @Override
//    public Result queryBlogOfFollow(Long max, Integer offset) {
//        // 获取当前用户
//        UserDTO user = UserHolder.getUser();
//        Long userId = user.getId();
//        // 查询当前用户的收件箱
//        String key = "feed:" + userId;
//        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet()
//                .reverseRangeByScoreWithScores(key, 0, max, offset, 5);
//        if (typedTuples == null || typedTuples.isEmpty()) {
//            return Result.ok("");
//        }
//        // 解析数据 blogId  minTime  offset   滚动分页查询
//        ArrayList<Long> ids = new ArrayList<>();
//        long minTime = 0;
//        int times = 1;
//        for (ZSetOperations.TypedTuple<String> typedTuple : typedTuples) {
//            //  id
//            String value = typedTuple.getValue();
//            ids.add(Long.valueOf(value));
//            //  分数
//            Long score = typedTuple.getScore().longValue();
//            if (score == minTime) {
//                times++;
//            } else {
//                minTime = score;
//                times = 1;
//            }
//        }
//        // 根据id查询blog
//        String idStr = StrUtil.join(",", ids);
//        List<Blog> blogs = query().in("id", ids).last("ORDER BY FIELD(id," + idStr + ")").list();
//        for (Blog blog : blogs) {
//            //  查询blog相关的用户
//            queryBlogUser(blog);
//            //  查询blog是否被点赞
//            isBlogLiked(blog);
//        }
//        // 封装并返回
//        ScrollResult scrollResult = new ScrollResult();
//        scrollResult.setList(blogs);
//        scrollResult.setOffset(times);
//        scrollResult.setMinTime(minTime);
//        return Result.ok(scrollResult);
//    }
//
//    /**
//     * @description: 查询博客对应的用户 并添加到博客实体类中
//     * @author gengpengfei
//     * @date 2022/10/22 15:16
//     */
//    public void queryBlogUser(Blog blog) {
//        Long userId = blog.getUserId();
//        User user = userService.getById(userId);
//        blog.setNickName(user.getNickName());
//        blog.setPicture(user.getPicture());
//    }
//}
//
