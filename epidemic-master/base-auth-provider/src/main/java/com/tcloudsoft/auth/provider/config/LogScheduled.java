package com.tcloudsoft.auth.provider.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tcloudsoft.auth.provider.model.Log;
import com.tcloudsoft.auth.provider.service.LogService;
import com.tcloudsoft.auth.provider.utils.DateUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Api(tags = "日志定时任务", description = "日志定时任务")
public class LogScheduled {

    @Autowired
    private LogService logService;

    @Scheduled(cron="0 59 23 * * ?")//每天晚上23点59分执行一次
    public void deletedLog(){
        try {
            //查询当天是否有日志记录，至少保留七天的日志数据
            Date now = new Date();
            Date begin = DateUtils.getDayFirstTime(now);
            Date end = DateUtils.getDayLastTime(now);
            QueryWrapper<Log> logQueryWrapper = Wrappers.query();
            logQueryWrapper.ge("creator_time",begin);
            logQueryWrapper.le("creator_time",end);
            List<Log> nowDateList = logService.list(logQueryWrapper);
            if(nowDateList.size()==0){
                log.info("----日志定时任务执行成功(今日无日志数据，不执行删除)");
                return;
            }
            //获取当前时间往前推10天的开始时间
            Date date = DateUtils.getStartBeforeDay(10);
            QueryWrapper<Log> query = Wrappers.query();
            //查询七天前的日志数据，并删除
            query.le("creator_time",date);
            List<Log> list = logService.list(query);
            Integer size = list.size();
            if(list.size()==0){
                log.info("----日志定时任务执行成功(无十日前日志数据)----");
                return;
            }
            for (Log log : list){
                logService.removeById(log);
            }
            //用下面这个批量删除的方法会报错
            //logService.removeByIds(list);
            log.info("----系统已清除十日前的日志数据（"+size+"条）----");
        }catch (Exception e){
            log.error("日志定时任务执行失败",e);
        }
    }
}
