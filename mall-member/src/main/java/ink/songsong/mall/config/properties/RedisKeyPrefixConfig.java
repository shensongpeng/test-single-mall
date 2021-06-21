package ink.songsong.mall.config.properties;/*
 *
 * @ClassName RedisKeyPrefixConfig
 * @Author shensongpeng
 * @Date 2021/6/17 :20:48
 * @Version 1.0
 * */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis.key")
@Data
public class RedisKeyPrefixConfig {
    private RedisKeyPrefixConfig.Prefix prefix;
    private RedisKeyPrefixConfig.Expire expire;

    @Data
    public static class Prefix{
        private String otpCode;

    }

    @Data
    public static class Expire{

        private Long otpCode;

    }
}
