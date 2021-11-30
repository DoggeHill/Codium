package sk.hyll.patrik.codium.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import sk.hyll.patrik.codium.controllers.services.BankService;
import sk.hyll.patrik.codium.model.CardOwner;

@Configuration
@Profile("local")
public class RedisInit implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${spring.dummy.generate}")
    private String active;

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
    BankService bankCardService;
    public RedisInit(BankService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(this.active.equals("true"))
        bankCardService.createDummyData();
    }
}
