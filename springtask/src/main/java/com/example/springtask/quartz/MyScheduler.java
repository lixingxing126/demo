package com.example.springtask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import java.time.LocalDateTime;

// 定时任务
@Slf4j
public class MyScheduler {

    public void scheduler () {
        try {
            // 1.创建Scheduler工厂, 获取scheduler实例
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            // 2.创建Job
            JobDetail job = JobBuilder
                    .newJob(MyJob.class)
                    .withIdentity("JobName","JobGroup")
                    .build();

            // 3.创建Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger","group")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ? "))
                    .startNow()
                    .build();

            // 配置任务拦截器
            scheduler.getListenerManager()
                    .addJobListener(new MyJobListener(),
                            KeyMatcher.keyEquals(new JobKey("JobName","JobGroup")));

            // 4.注册任务
            scheduler.scheduleJob(job,trigger);

            // 5.启动 调度器 scheduler
            scheduler.start();
            log.info("启动时间" + LocalDateTime.now());

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}

