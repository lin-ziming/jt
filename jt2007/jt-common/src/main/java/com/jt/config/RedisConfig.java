package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

@Configuration  //标识我是配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
    /**
     * SpringBoot整合Redis分片，实质：ShardedJedis对象，交给容器管理
     */
    @Value("${redis.nodes}")
    private String nodes;   //node,node,node

    @Bean
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
