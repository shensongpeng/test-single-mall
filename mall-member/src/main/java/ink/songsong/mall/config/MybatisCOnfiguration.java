package ink.songsong.mall.config;/*
 *
 * @ClassName MybatisCOnfiguration
 * @Author shensongpeng
 * @Date 2021/6/21 :9:59
 * @Version 1.0
 * */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("ink.songsong.mall.mapper")
@Configuration
public class MybatisCOnfiguration {
}
