package com.jt;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestRedis {
    /**
     * 1.实现redis测试
     * 报错检查：
     * 1.检查redis.conf配置文件：1.ip绑定问题 2.保护模式问题 3.后台启动问题
     * 2.检查redis启动方式   redis-server redis.conf
     * 3.检查防火墙
     */
    @Test
    public void test01(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
        jedis.set("2007", "redis入门案例");
        System.out.println(jedis.get("2007"));
    }

    /**
     * 判断是否有key数据，如果没有则新增数据，如果没有则放弃新增
     */
    @Test
    public void test02(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
//        if(!jedis.exists("2007")){ //判断数据是否存在
//            jedis.set("2007", "测试案例222");
//        }
//        System.out.println(jedis.get("2007"));
        /**
         * setnx()作用：如果有数据，则不做处理
         */
        jedis.setnx("2007", "测试高级用法");
        System.out.println(jedis.get("2007"));
    }

    /**
     * 需求：向redis中添加一个数据。set-key-value,要求添加超时时间100秒
     * 隐藏bug:
     */
    @Test
    public void test03() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.126.129",6379);
//        jedis.set("2007", "测试时间");
//        int a = 1/0;
//        jedis.expire("2007", 10);
        /**
         * expire隐藏含义：业务需要 到期要删除数据，但是这样不安全
         * 如在expire执行前出错，则数据会永远留在内存中
         * 即死锁操作
         * 隐藏bug: 代码执行过程中，如果报错，则可能删除失败
         * 原子性： 要么同时成功，要么同时失败
         * 解决办法： 将入库操作/超时时间一起设定。setex
         */
        jedis.setex("2007", 100, "测试时间");
        Thread.sleep(3000);
        System.out.println(jedis.ttl("2007")+"秒");//获取当前数据的剩余存活时间
    }

    /**
     * 1.如果数据存在则不操作数据   setnx
     * 2.同时设定超时时间，注意原子性 setex
     * 参数说明：
     *  1. XX = "xx";  只有key，则进行操作
     *  2. NX = "nx";  没有key，则进行写操作
     *  3. PX = "px";  毫秒
     *  4. EX = "ex";  秒
     */
    @Test
    public void test04(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
        SetParams setParams = new SetParams();
        setParams.nx().ex(100);
        jedis.set("2007","aaa",setParams);
        System.out.println(jedis.get("2007"));
    }
    @Test
    public void testHash() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.126.129",6379);
        jedis.hset("person", "id","18");
        jedis.hset("person", "name","hash测试");
        jedis.hset("person", "age","2");
        Map<String, String> map = jedis.hgetAll("person");
        Set<String> set = jedis.hkeys("person");  //获取所有key
        List<String> list = jedis.hvals("person");
    }
    @Test
    public void testList() {
        Jedis jedis = new Jedis("192.168.126.129",6379);
        jedis.lpush("list", "1","2","3","4");
        System.out.println(jedis.rpop("list"));
    }
    @Test
    public void testTx(){
        Jedis jedis = new Jedis("192.168.126.129",6379);
        //1.开启事务
        Transaction transaction = jedis.multi();
        try {
            transaction.set("a","a");
            transaction.set("b","b");
            transaction.set("c","c");
            transaction.exec(); //提交事务
        }catch (Exception e){
            transaction.discard();
        }
    }

}
