package com.bj58.fang.task;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * https://www.cnblogs.com/linjiqin/archive/2013/07/08/3178452.html
 * 天 和 星  矛盾所以至少一个是?
 * @ClassName:Easy
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月3日
 * @Version V1.0
 * @Package com.bj58.fang.task
 */
public class Easy {

	public static void main(String[] args) {
		test2();
	}

	private static void test() {
		SchedulerFactory factory = new StdSchedulerFactory();
		try {
			Scheduler scheduler = factory.getScheduler();
			JobDetail job = new JobDetail("one job", "one group", OneJob.class);
//			CronTrigger trigger = new CronTrigger("ss", "xxx", "0/5 * * * * ?");
			CronTrigger trigger = new CronTrigger("one trigger", "one group", "one job", "one group", "0/5 * * * * ?");
			Date date = scheduler.scheduleJob(job, trigger);
			System.out.println(date);
//			CronExpression
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void test2() {
		SchedulerFactory factory = new StdSchedulerFactory();
		try {
			Scheduler scheduler = factory.getScheduler();
			Job jj =  new Job() {
				@Override
				public void execute(JobExecutionContext context) throws JobExecutionException {
					System.out.println(context.getJobDetail().getName());
				}
			};
			
			JobDetail job = new JobDetail("one job", "one group", jj.getClass());
//			CronTrigger trigger = new CronTrigger("ss", "xxx", "0/5 * * * * ?");
			CronTrigger trigger = new CronTrigger("one trigger", "one group", "one job", "one group", "0/5 * * * * ?");
			Date date = scheduler.scheduleJob(job, trigger);
			
			System.out.println(date);
//			CronExpression
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
