package com.gpf.animal.service.impl;

import ch.qos.logback.core.util.CachingDateFormatter;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gpf.animal.common.Result;
import com.gpf.animal.dao.AdoptDao;
import com.gpf.animal.dto.AdoptDTO;
import com.gpf.animal.entity.Adopt;
import com.gpf.animal.entity.Pet;
import com.gpf.animal.entity.User;
import com.gpf.animal.service.AdoptService;
import com.gpf.animal.service.PetService;
import com.gpf.animal.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (Adopt)表服务实现类
 *
 * @author makejava
 * @since 2022-11-10 09:14:03
 */
@Service("adoptService")
public class AdoptServiceImpl extends ServiceImpl<AdoptDao, Adopt> implements AdoptService {

    @Resource
    private UserService userService;
    @Resource
    private PetService petService;

    @Override
    public Result insertAdopt(Adopt adopt) {
        Long petId = adopt.getPetId();
        Long userId = adopt.getUserId();
        if (petId != null && userId != null) {
            LambdaQueryWrapper<Adopt> adoptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            adoptLambdaQueryWrapper.eq(Adopt::getPetId, petId);
            adoptLambdaQueryWrapper.eq(Adopt::getUserId, userId);
            adoptLambdaQueryWrapper.last("limit 1");
            Adopt newAdopt = getOne(adoptLambdaQueryWrapper);

            if (newAdopt == null) {
                long timeMillis = System.currentTimeMillis();
                adopt.setCreateTime(timeMillis);
                save(adopt);
                return Result.ok("提交成功！请耐心等待审批");
            }
        }
        return Result.fail("请勿重复申请领养");
    }

    @Override
    public Result updateAdopt(Adopt adopt) {
        updateById(adopt);
        return Result.ok("修改成功");
    }

    @Override
    public Result deleteAdopt(Long id) {
        removeById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result getAdopt(Long id) {
        LambdaQueryWrapper<Adopt> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Adopt::getUserId, id);
        List<Adopt> adoptList = list(wrapper);
        List<AdoptDTO> adoptDTOS = new ArrayList<>();
        adoptDTOS = adoptCopyAdoptDTO(adoptList, adoptDTOS);
        return Result.ok(adoptDTOS);
    }

    @Override
    public Result updateStatus(int status, int id) {
        Adopt adopt = getById(id);
        adopt.setStatus(status);
        updateById(adopt);

        return Result.ok("修改成功");
    }

    @Override
    public Result getAdoptPage(int page, int pageSize) {
        Page<AdoptDTO> adoptDTOPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Adopt> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Adopt::getId);
        List<Adopt> adoptList = list(wrapper);
        List<AdoptDTO> adoptDTOS = new ArrayList<>();
        adoptDTOS = adoptCopyAdoptDTO(adoptList, adoptDTOS);
        List<AdoptDTO> newListDTO;
        if (page * pageSize < adoptDTOS.size()) {
            newListDTO = adoptDTOS.subList((page - 1) * pageSize, page * pageSize);
        } else {
            newListDTO = adoptDTOS.subList((page - 1) * pageSize, adoptDTOS.size());
        }
        adoptDTOPage.setRecords(newListDTO);
        adoptDTOPage.setTotal(adoptDTOS.size());
        System.out.println(adoptDTOPage.getRecords());
        return Result.ok(adoptDTOPage);
    }

    public List<AdoptDTO> adoptCopyAdoptDTO(List<Adopt> adoptList, List<AdoptDTO> adoptDTOS) {
        for (Adopt adopt : adoptList) {
            AdoptDTO adoptDTO = new AdoptDTO();
            BeanUtils.copyProperties(adopt, adoptDTO);
            Long createTime = adopt.getCreateTime();
            DateTime dateTime = new DateTime(createTime);
            adoptDTO.setCreateTime(dateTime.toString());
            Pet pet = petService.getById(adopt.getPetId());
            adoptDTO.setPetName(pet.getNickName());
            User user = userService.getById(adopt.getUserId());
            adoptDTO.setUserAddress(user.getAddress());
            adoptDTO.setUserPhone(user.getTelephone());
            adoptDTO.setNickName(user.getNickName());
            adoptDTOS.add(adoptDTO);
        }
        return adoptDTOS;
    }

    /**
     * 获取echart数据
     */
    @Override
    public Result getAdoptEchartData(String dateConditions) {
        HashMap<String, List> echartData = new HashMap<>();
        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if ("day".equals(dateConditions)) {
            for (int i = 0; i < 7; i++) {
                LocalDate localDate = currentDate.minusDays(i);
                labels.add(localDate.toString());
                LocalDateTime start = localDate.atStartOfDay();
                LocalDateTime end = start.withHour(23).withMinute(59).withSecond(59);
                values = getTimeNum(start, end, values);
            }
        } else if ("month".equals(dateConditions)) {
            for (int i = 0; i < 4; i++) {
                LocalDate date = currentDate.minusWeeks(i);
                LocalDate monday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate sunday = monday.plusDays(6);
                LocalDateTime start = LocalDateTime.of(monday, LocalTime.MIDNIGHT);
                LocalDateTime end = LocalDateTime.of(sunday, LocalTime.of(23, 59, 59));
                values = getTimeNum(start, end, values);
                if (!date.isBefore(currentDate.minusWeeks(4))) {
                    String strStart = dateFormatter.format(start.toLocalDate());
                    String strEnd = dateFormatter.format(end.toLocalDate());
                    labels.add(strStart + " - " + strEnd);
                }
            }
        } else if ("year".equals(dateConditions)) {
            for (int i = 0; i < 12; i++) {
                LocalDate date = currentDate.minusMonths(i);
                LocalDate firstDayOfMonth = date.withDayOfMonth(1);
                LocalDate lastDayOfMonth = date.withDayOfMonth(date.lengthOfMonth());
                LocalDateTime start = LocalDateTime.of(firstDayOfMonth.minusMonths(1), LocalTime.MIDNIGHT);
                LocalDateTime end = LocalDateTime.of(lastDayOfMonth, LocalTime.of(23, 59, 59));
                values = getTimeNum(start, end, values);
                if (!date.isBefore(currentDate.minusYears(1))) {
                    String strStart = dateFormatter.format(start.toLocalDate());
                    String strEnd = dateFormatter.format(end.toLocalDate());
                    labels.add(strStart);
                }
            }
        }
        echartData.put("labels", labels);
        echartData.put("values", values);
        return Result.ok(echartData);
    }

    public ArrayList<Integer> getTimeNum(LocalDateTime start, LocalDateTime end, ArrayList<Integer> values) {
        long startM = start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endM = end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        LambdaQueryWrapper<Adopt> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Adopt::getCreateTime, startM, endM);
        List<Adopt> list = list(wrapper);
        values.add(list.size());
        return values;
    }

}

