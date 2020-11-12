package com.jt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Aspect  //标识我是一个切面
//@Component  //交给Spring容器管理
public class CacheAOP1 {
    /**
     * 切面 = 切入点表达式 + 通知方法
     *
     * @Pointcut("bean(itemCatServiceImpl)")
     * @Pointcut("within(com.jt.service.ItemCatServiceImpl)")
     * @Pointcut("within(com.jt.service.*)") //  .* 一级包路径  ..* 所有子孙后代包
     * @Pointcut("execution(返回值类型 包名.类名.方法名(参数列表))")
     *
     * @Pointcut("execution(* com.jt.service..*.add*(..))")
     * 含义：返回值类型任意类型  在com.jt.service下的所有子孙类，以add开头的方法，任意参数类型
     */
//    @Pointcut("execution(* com.jt.service..*.*(..))")
    public void pointCut(){}

    /**
     * 需求：1.获取目标方法的路径
     *      2.获取目标方法的参数
     *      3.获取目标方法的名称
     */
//    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        String classNamePath = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("方法路径:"+classNamePath);
        System.out.println("方法参数:"+ Arrays.toString(args));
        System.out.println("方法名称:"+methodName);
    }

//    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        /**
         * 如果有下一个通知，就执行下一个通知，如果没有就执行目标方法(业务方法)
         */
        try {
            System.out.println("环绕通知开始");
            Object obj = joinPoint.proceed();
            System.out.println("环绕通知结束");
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException(throwable);
        }
    }
}
