package com.jt.aop;


import com.jt.anno.CacheFind;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

import java.util.Arrays;

@Aspect  //标识我是一个切面
@Component  //交给Spring容器管理
public class CacheAOP {

    @Autowired
    private JedisCluster jedis;    //完成集群对象的注入
//    private ShardedJedis jedis;  //完成分片对象的注入
//    private Jedis jedis;  //单台redis
    /**
     * 注意事项:  当有多个参数时,joinPoint必须位于第一位.
     * 需求:
     *      1.准备key= 注解的前缀 + 用户的参数
     *      2.从redis中获取数据
     *         有: 从缓存中获取数据之后,直接返回值
     *         没有: 查询数据库之后再次保存到缓存中即可.
     *
     * 方法:
     *      动态获取注解的类型,看上去是注解的名称,但是实质是注解的类型. 只要切入点表达式满足条件
     *      则会传递注解对象类型.
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind) throws Throwable {
        Object result = null;  //定义返回值对象
        String preKey = cacheFind.preKey();
        String key = preKey + "::" + Arrays.toString(joinPoint.getArgs());

        //1.校验redis中是否有数据
        if(jedis.exists(key)){
            //如果数据存在,需要从redis中获取json数据,之后直接返回
            String json = jedis.get(key);
            //1.获取方法对象,   2.获取方法的返回值类型
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            //2.获取返回值类型
            Class returnType = methodSignature.getReturnType();
            //returnType不要写Object，因为容易翻车！！！
            result = ObjectMapperUtil.toObject(json,returnType);
            System.out.println("AOP查询redis缓存!!!");
        }else{
            //代表没有数据,需要查询数据库
            result = joinPoint.proceed();
            //将数据转化为JSON
            String json = ObjectMapperUtil.toJSON(result);
            if(cacheFind.seconds() > 0){
                jedis.setex(key,cacheFind.seconds(),json);
            }else{
                jedis.set(key,json);
            }
            System.out.println("AOP查询数据库!!!");
        }
        return result;
    }

/*    @Around("@annotation(com.jt.anno.CacheFind)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //1.获取目标对象的Class类型
        Class targetClass = joinPoint.getTarget().getClass();
        //2.获取目标方法名称
        String methodName = joinPoint.getSignature().getName();
        //3.获取参数类型
        Object[] argsObj = joinPoint.getArgs();
        Class[]  argsClass = null;
        //4.对象转化为class类型
        if(argsObj.length>0){
            argsClass = new Class[argsObj.length];
            for(int i=0;i<argsObj.length;i++){
                argsClass[i] = argsObj[i].getClass();
            }
        }
        //3.获取方法对象
        Method targetMethod = targetClass.getMethod(methodName,argsClass);

        //4.获取方法上的注解
        if(targetMethod.isAnnotationPresent(CacheFind.class)){
            CacheFind cacheFind = targetMethod.getAnnotation(CacheFind.class);
            String key = cacheFind.preKey() + "::"
                    +Arrays.toString(joinPoint.getArgs());
            System.out.println(key);
        }

        Object object = joinPoint.proceed();
        System.out.println("环绕开始后");
        return object;
    }
*/

}
