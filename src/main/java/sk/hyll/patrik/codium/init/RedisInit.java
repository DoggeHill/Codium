package sk.hyll.patrik.codium.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import sk.hyll.patrik.codium.model.CardOwner;

@Configuration
//@EnableRedisRepositories(basePackages = "sk.hyll.patrik.codium.controllers.redisRepositories")

public class RedisInit {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<CardOwner, Object> redisTemplate() {
        RedisTemplate<CardOwner, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
