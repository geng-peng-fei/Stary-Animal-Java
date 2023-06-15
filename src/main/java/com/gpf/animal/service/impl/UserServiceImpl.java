package com.gpf.animal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.UserDao;
import com.gpf.animal.dto.LoginFormDTO;
import com.gpf.animal.dto.UserDTO;
import com.gpf.animal.entity.Admin;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.UserService;
import com.gpf.animal.util.RegexUtils;
import com.gpf.animal.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:04
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发送验证码
     */
    @Override
    public Result sendCode(String phone) {
        //1.校验手机号的格式
        if (RegexUtils.isPhoneInvalid(phone)) {
            //1.1 校验失败返回错误信息
            return Result.fail("手机号格式错误！");
        }
        //1.2 校验成功随机验证码
        String code = RandomUtil.randomNumbers(6);
        //1.2.1 将生成的验证码存入redis中
        stringRedisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        //1.2.2 将生成的验证码发送给用户（这里为了方便选择以日志的方式打印验证码）
        log.debug("code:{}", code);
        //2 返回结果
        return Result.ok(code);
    }

    /**
     * 用户登陆/注册
     */
    @Override
    public Result login(LoginFormDTO loginForm) {
        if (loginForm.getPhone() != null && loginForm.getCode() != null) {
            //1 获取手机号和验证码
            String phone = loginForm.getPhone();
            String code = loginForm.getCode();
            //2 进行校验
            String cacheCode = stringRedisTemplate.opsForValue().get(phone);
            //2.1 校验失败  返回错误信息
            if (cacheCode == null || !code.equals(cacheCode)) {
                return Result.fail("验证码错误!");
            }
            //3 校验成功  根据手机号查询用户是否存在
            User user = query().eq("telephone", phone).one();
            //  用户不存在  保存用户到数据库
            if (user == null) {
                User newUser = new User();
                newUser.setNickName("user" + RandomUtil.randomString(10));
                newUser.setTelephone(phone);
                save(newUser);
            }
            // 用户存在
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            String token = String.valueOf(UUID.randomUUID(true));
            userDTO.setToken(token);
            return Result.ok(userDTO);
        }
        return Result.fail("请检查手机号或者验证码");
    }

    /**
     * 用户登出
     */
    @Override
    public Result loginOut(String token) {
        return Result.ok("退出登陆");
    }

    /**
     * 用户中心信息
     */
    @Override
    public Result userCenter(Long id) {
        // 根据用户id查询用户
        User user = getById(id);
        // 返回user
        return Result.ok(user);
    }

    /**
     * 用户中心修改信息
     *
     * @param user 用户
     */
    @Override
    public Result updateUser(User user) {
        updateById(user);
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        String token = String.valueOf(UUID.randomUUID(true));
        userDTO.setToken(token);
        return Result.ok(userDTO);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Result userPage(int page, int pageSize, String name) {
        //分页构造器
        Page<User> userPage = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //                模糊查询     name不为空          方法引用 对象 ：：实例方法  值
        wrapper.like(StringUtils.isNotEmpty(name), User::getName, name);
        //                   降序排列    方法引用 对象 ：：实例方法
        wrapper.orderByDesc(User::getId);
        //调page方法
        page(userPage, wrapper);
        //返回数据
        return Result.ok(userPage);
    }

    /**
     * 修改用户账号状态
     *
     * @param user
     * @return
     */
    @Override
    public Result updateUserStatus(User user) {
        updateUser(user);
        return Result.ok("修改成功");
    }

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public Result getUserById(Long id) {
        User user;
        try {
            user = getById(id);
        } catch (Exception e) {
            return Result.fail("查询用户信息失败");
        }
        return Result.ok(user);
    }

    /**
     * 用户echart数据
     * @return
     */
    @Override
    public Result getUserEchartData() {
        HashMap<String, List> echartData = new HashMap<>();
        ArrayList<String> labels= new ArrayList<>();
        ArrayList<Integer> values= new ArrayList<>();
        List<User> userList = list();
        int manSum = 0;
        int womanSum = 0;
        for (User user : userList) {
            if (user.getSex() == 1){
                manSum += 1;
            }else if (user.getSex() == 0){
                womanSum += 1;
            }
        }
        labels.add("男");
        labels.add("女");
        values.add(manSum);
        values.add(womanSum);
        echartData.put("labels", labels);
        echartData.put("values", values);
        return Result.ok(echartData);
    }
/**
     * //  对用户的关键信息打码
     *             String telephone = user.getTelephone();
     *             user.setTelephone(telephone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
     *             String name = user.getName();
     *             if (name.length() == 2) {
     *                 user.setName(name.substring(0, 1) + "*");
     *             } else {
     *                 StringBuffer middle = new StringBuffer();
     *                 for (int i = 0; i < name.substring(1, name.length() - 1).length(); i++) {
     *                     middle.append("*");
     *                 }
     *                 user.setName(name.substring(0, 1) + middle + name.substring(name.length() - 1));
     *             }
     *
     *             String idCard = user.getIdCard();
     *             if (StringUtils.isNotEmpty(idCard)) {
     *                 if (idCard.length() == 15) {
     *                     user.setIdCard(idCard.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2"));
     *                 }
     *                 if (idCard.length() == 18) {
     *                     user.setIdCard(idCard.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2"));
     *                 }
     *             }
     *
     *             String address = user.getAddress();
     *             if (StringUtils.isNotEmpty(address)) {
     *                 int length = address.length();
     *                 int indes = address.indexOf("区");
     *                 if (indes == -1) {
     *                     indes = address.indexOf("市");
     *                 }
     *                 address = address.substring(0, indes + 1);
     *                 StringBuffer middle = new StringBuffer();
     *                 for (int i = 0; i < length - indes; i++) {
     *                     middle.append("*");
     *                 }
     *                 user.setAddress(address + middle);
     *             }
     */
}

