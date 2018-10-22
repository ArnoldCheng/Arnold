package cn.zzh.foreground_client.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 *
 * @Description:
 * @author: 快乐水 青柠可乐
 * @date: 下午7:43 2018/10/4
 * @param:
 * @return:
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
