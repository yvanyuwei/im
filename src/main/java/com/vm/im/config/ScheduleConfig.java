package com.vm.im.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

/**
 * 定时任务线程池
 *
 * @author zhangqi
 * @since 2019-02-19
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //定时任务线程池最好比定时任务多一条
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
    }
}