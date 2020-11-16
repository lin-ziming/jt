package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration  //标识我是配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
    /**
     * 实现redis集群操作
     */
    @Value("${redis.nodes}")
    private String nodes;   //node,node,node

    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> nodeSet = new HashSet<>();
        String[] nodeArray = nodes.split(",");
        for (String node : nodeArray) { //host:port
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            nodeSet.add(new HostAndPort(host,port));
        }
        return new JedisCluster(nodeSet);
    }

/**
 * SpringBoot整合Redis分片，实质：ShardedJedis对象，交给容器管理
 */
/*    @Bean
    public ShardedJedis shardedJedis(){
        List<JedisShardInfo> shards = new ArrayList<>();
        String[] nodeArray = nodes.split(",");
        for (String node : nodeArray) {
            String host = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            shards.add(new JedisShardInfo(host,port));
        }
        return new ShardedJedis(shards);
    }
    */

/**
 * 单台redis
 */
//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.port}")
//    private Integer port;
//
//    @Bean
//    public Jedis jedis(){
//        return new Jedis(host,port);
//    }


}
