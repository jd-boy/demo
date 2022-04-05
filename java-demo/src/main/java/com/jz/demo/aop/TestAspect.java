package com.jz.demo.aop;

import java.util.Map;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Auther jd
 */
@Aspect
@Component
public class TestAspect {

  @Around("execution(public * com.jz.demo.aop.AopTestController.get())")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("around begin");
    Object resust = joinPoint.proceed();
    System.out.println("around end");
    return resust;
  }

  @Before("execution(public * com.jz.demo.aop.AopTestController.get())")
  public void before(JoinPoint joinPoint) {
    System.out.println("before....");
  }

  @SneakyThrows
  @After("execution(public * com.jz.demo.aop.AopTestController.get())")
  public void after(JoinPoint joinPoint) {
//    Thread.sleep(5000);
    System.out.println("after....");
  }

  @AfterReturning(pointcut = "execution(public * com.jz.demo.aop.AopTestController.get())")
  public void afterReturing() throws InterruptedException {
    System.out.println("after teturing...");
//    System.out.println("id=" + id);
  }
}
