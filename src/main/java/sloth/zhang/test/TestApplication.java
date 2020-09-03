package sloth.zhang.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@MapperScan("sloth.zhang.test.dao")
@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200, redisFlushMode = RedisFlushMode.IMMEDIATE)
public class TestApplication {

    @Bean
    public WebSecurityFilter accessFilter() {
        return new WebSecurityFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
