package com.lplb.sys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author（作者）：BealHe
 * @Date（日期）：2023-07-29 16:45
 * @Description（描述）：ThreadConfig
 */
@Configuration
public class ThreadConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        //@param corePoolSize: 池中一直保持的线程的数量，即使线程空闲。除非设置了 allowCoreThreadTimeOut
        return new ThreadPoolExecutor(500,
                //@param maximumPoolSize: 池中允许的最大的线程数
                5000,
                //@param keepAliveTime: 当线程数大于核心线程数的时候，线程在最大多长时间没有接到新任务就会终止释放， 最终线程池维持在 corePoolSize 大小
                10,
                //@param 时间单位
                TimeUnit.SECONDS,
                //@param workQueue: 阻塞队列，用来存储等待执行的任务，如果当前对线程的需求超过了 corePoolSize 大小，就会放在这里等待空闲线程执行。
                new LinkedBlockingDeque<>(3500000),
                //@param threadFactory: 创建线程的工厂，比如指定线程名等
                Executors.defaultThreadFactory(),
                //@param handler: 拒绝策略，如果线程满了，线程池就会使用拒绝策略。
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
