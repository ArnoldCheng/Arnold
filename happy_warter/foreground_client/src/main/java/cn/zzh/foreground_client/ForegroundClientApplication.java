package cn.zzh.foreground_client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import sun.tools.java.ClassPath;

/**
 * @author a1
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.zzh.foreground_client.project.dao")
@PropertySource(value = {"classpath:config/aLiMessageAPI.properties"},ignoreResourceNotFound = true,encoding = "utf-8")
public class ForegroundClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForegroundClientApplication.class, args);
    }
}
