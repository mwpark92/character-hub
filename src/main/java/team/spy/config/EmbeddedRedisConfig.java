package team.spy.config;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

@Slf4j
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

	@Value("${spring.redis.port}")
    private int redisPort;
	
	
    private RedisServer redisServer;

   
    
    @PostConstruct
    public void redisServer() throws IOException {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
	
}
