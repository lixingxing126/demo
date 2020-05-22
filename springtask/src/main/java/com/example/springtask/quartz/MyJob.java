package com.example.springtask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * MyJob 用于打印测试的 JOB 类
 */
@Slf4j
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        log.info("MyJob key :" + key);
        Date fireTime = context.getFireTime();
        LocalDateTime localDateTime = fireTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        log.info("MyJob 实际执行的开始时间 ：" + localDateTime);
    }

}
