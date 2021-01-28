package com.cmtt.base.cron;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cmtt.base.controller.LbAdController;
import com.cmtt.base.entity.LbPost;
import com.cmtt.base.service.ILbPostService;
import com.cmtt.base.utils.RC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    private final Logger logger = LoggerFactory.getLogger(SaticScheduleTask.class);

    @Autowired
    private ILbPostService lbPostService;


    //3.添加定时任务
    @Scheduled(cron = "0 0/1 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {

        logger.info("执行静态定时任务时间: " + LocalDateTime.now());
        lbPostService.update(Wrappers.<LbPost>lambdaUpdate()
                .eq(LbPost::getIsPreview, 2)
                .eq(LbPost::getStatus, RC.B_NORMAL.code())
                .lt(LbPost::getPublishedAt,LocalDateTime.now())
                .set(LbPost::getIsPreview,1)
        );

    }
}