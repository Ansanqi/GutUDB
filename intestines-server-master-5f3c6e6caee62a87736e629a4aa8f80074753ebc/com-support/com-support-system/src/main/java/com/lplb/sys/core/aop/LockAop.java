
package com.lplb.sys.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *   @author Ray-zy
 *   @since 2020/11/27 15:32
 **/
@Component
@Aspect
public class LockAop {
    private static final Logger log = LoggerFactory.getLogger(LockAop.class);

    /**
     * 非公平锁
     */
    private static Lock lock = new ReentrantLock();

    @Pointcut("@annotation(com.lplb.core.annotion.ThreadLock)")
    public void lockAspect() {
        //Do Nothing
    }

    @Around("lockAspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        lock.lock();
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }
        return obj;
    }
}
