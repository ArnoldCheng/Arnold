package cn.zzh.foreground_client;
import cn.zzh.foreground_client.project.service.Tools;
import cn.zzh.foreground_client.project.service.impl.ToolsImpl;
import cn.zzh.foreground_client.project.service.impl.UserServiceImpl;
import cn.zzh.foreground_client.project.tools.redis.Redis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午5:07 2018/10/14
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    private final static Logger logger = LoggerFactory.getLogger(ForegroundClientApplicationTests.class);
    /**
     *Service层测试
     */

    private static final String MSG="msg";
    private static final String THETIME="msgTheTime";
    private static final String AMOUNT="amount";
    private static final String ERRORS="errors";
    private static final String FIRSTTIME="firstTIme";
    private static final int    MAXTIMES=20;


    private final static long S          =              1;
    private final static long MIN        =             60;
    private final static long HOUR       =           3600;
    private final static long DAY        =          86400;
    private final static long MOUTH      =        2592000;
    private final static long TEMPORARY  =            900;
    private long ranTime=(long)(15*DAY +Math.random()*(30*DAY -15*DAY +1));
    private Long intervalTime;
    private int times;
    private Long totalMilliSeconds = System.currentTimeMillis();
    private Long totalSeconds = totalMilliSeconds / 1000;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    Tools tools;

    @Autowired
    ToolsImpl tool;

    @Autowired
    Redis redis;

    @Test
    public void tools(){
        String phoneNumber ="13824429757";

        for(int i=0;i<3;i++){
            tools.sendAliMsg(phoneNumber);
            logger.info("____________________________________________________");
            logger.info("redis当次请求的时间是:"+redis.get(phoneNumber+THETIME));
            logger.info("redis第一次请求时间是"+redis.get(phoneNumber+FIRSTTIME));
            logger.info("redis该用户总共请求的次数"+redis.get(phoneNumber+AMOUNT));
            logger.info("____________________________________________________");
        }
    }

    @Test
    public void pwdTest(){
        String phoneNumber="13824429757";
        redis.set(phoneNumber+MSG,"123",TEMPORARY);
        logger.info(String.valueOf(redis.get("13824429757"+MSG)));
    }

    @Test
    public void a1() {

//        boolean i=tool.idCardVertify("210002199002301531");
//        logger.info(String.valueOf(i));

//        boolean i=tool.cardVertify("6227003326090671990");
//        logger.info(String.valueOf(i));

        boolean i=tool.chineseVertify("郑志航");
        logger.info(String.valueOf(i));
    }


}
