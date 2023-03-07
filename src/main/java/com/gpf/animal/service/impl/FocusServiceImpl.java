//package com.gpf.animal.service.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.gpf.animal.common.Result;
//import com.gpf.animal.dao.FocusDao;
//import com.gpf.animal.dto.UserDTO;
//import com.gpf.animal.entity.Focus;
//import com.gpf.animal.service.FocusService;
//import com.gpf.animal.util.UserHolder;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * (Focus)表服务实现类
// *
// * @author makejava
// * @since 2022-11-10 09:14:03
// */
//@Service("focusService")
//public class FocusServiceImpl extends ServiceImpl<FocusDao, Focus> implements FocusService {
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * @description: 关注/取关功能
//     * @author gengpengfei
//     * @date 2022/11/22 17:21
//     */
//    @Override
//    public Result follow(Long focusedUserId, Boolean isFocus) {
//        Long focusUserId = UserHolder.getUser().getId();
//        //1.    判断是关注还是取关
//        if (isFocus) {
//            //2.    关注
//            Focus focus = new Focus();
//            //  被关注用户的id
//            focus.setFocusedUserId(focusedUserId);
//            //  关注用户的id
//            focus.setFocusUserId(focusUserId);
//            boolean isSuccess = save(focus);
//            //  判断插入数据库的操作是否完成
//            if (isSuccess) {
//                //  完成  缓存到Redis的Set集合中
//                String key = "follows:" + focusUserId;
//                stringRedisTemplate.opsForSet().add(key, focusedUserId.toString());
//            }
//        } else {
//            //3.    取关
//            LambdaQueryWrapper<Focus> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(Focus::getFocusUserId, focusUserId);
//            wrapper.eq(Focus::getFocusedUserId, focusedUserId);
//            //wrapper.eq("user_id", userId).eq("follow_user_id", followUserId);
//            boolean isSuccess = remove(wrapper);
//            //  判断删除数据库数据的操作是否完成
//            if (isSuccess) {
//                //  完成  移除redis中的缓存
//                String key = "follows:" + focusUserId;
//                stringRedisTemplate.opsForSet().remove(key, focusedUserId.toString());
//            }
//        }
//        return Result.ok();
//    }
//
//    /**
//     * @description: 判断是否关注
//     * @author gengpengfei
//     * @date 2022/11/22 17:21
//     */
//    @Override
//    public Result isFollow(Long focusedUserId) {
//        Long focusUserId = UserHolder.getUser().getId();
//        //  查询是否关注
//        //Integer count = query().eq("user_id", userId).eq("follow_user_id", followUserId).count();
//        LambdaQueryWrapper<Focus> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Focus::getFocusUserId, focusUserId);
//        wrapper.eq(Focus::getFocusedUserId, focusedUserId);
//        long count = count(wrapper);
//        return Result.ok(count > 0);
//    }
//
//    /**
//     * @description: 共同关注
//     * @author gengpengfei
//     * @date 2022/11/23 08:26
//     */
//    @Override
//    public Result followCommons(Long id) {
//        //  获取当前登陆用户
//        Long focusUserId = UserHolder.getUser().getId();
//        String key1 = "follows:" + focusUserId;
//        //  当前用户和目标用户的共同关注（交集）
//        String key2 = "follows:" + id;
//        Set<String> intersect = stringRedisTemplate.opsForSet().intersect(key1, key2);
//        //
//        if (intersect == null || intersect.isEmpty()) {
//            //
//            return Result.ok(Collections.emptyList());
//        }
//        //
//        List<Long> ids = intersect.stream().map(Long::valueOf).collect(Collectors.toList());
//        List<UserDTO> userDTOS = ids.stream().map(user -> BeanUtil.copyProperties(user, UserDTO.class)).collect(Collectors.toList());
//        //  User对象转化为UserDTO对象
//        return Result.ok(userDTOS);
//    }
//}
//
