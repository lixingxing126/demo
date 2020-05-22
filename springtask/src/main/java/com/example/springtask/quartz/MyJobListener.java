package com.example.springtask.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.util.ObjectUtils;

/**
 * 自定义 任务监听器
 */
@Slf4j
public class MyJobListener implements JobListener {

    @Override
    public String getName() {
        return "任务监听器";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        // 开始执行
        JobKey key = context.getJobDetail().getKey();
        log.info("开始执行 JOB " + key);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // 执行否决了
        JobKey key = context.getJobDetail().getKey();
        log.info("执行错误 JOB " + key);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        // 执行完成
        if (!ObjectUtils.isEmpty(jobException)) {
            log.error("任务执行错误，抛出异常：" + jobException.getMessage());
        } else {
            JobKey key = context.getJobDetail().getKey();
            log.info("执行完成 JOB " + key);
        }
    }
}
