package cn.zzh.foreground_client;
import cn.zzh.foreground_client.project.tools.aLiAPI.AliMessage;
import cn.zzh.foreground_client.project.tools.normal.Phone;
import cn.zzh.foreground_client.project.tools.redis.Redis;
import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
public class ToolsTest {
    private final static Logger logger = LoggerFactory.getLogger(ForegroundClientApplicationTests.class);
    @Autowired
    AliMessage aliMessage;
    @Autowired
    Phone phone;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    Redis redis;
    @Test
    public void aLiMessageTest(){
        try {
            aliMessage.sendSms("123","0000");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rdsTest(){
        redisTemplate.opsForValue().set("999","9999");
        System.out.println("-----------------------------");
        System.out.println(redisTemplate.opsForValue().get("999"));
        System.out.println("-----------------------------");
    }

    @Test
    public void stringRdsTest(){
        redisTemplate.opsForValue().get("999");
    }

    @Test
    public void phoneTest(){
        logger.info(String.valueOf(phone.isNumber("123e")));
    }

    //redis自增测试
    @Test
    public void redis(){
        redis.set("t",1,900);
        logger.info("key为t的初始值"+redis.get("t"));
        for(int i=0;i<5;i++){
            redisTemplate.opsForValue().increment("t",1);
            logger.info("key为t的缓存，第"+i+"次自增的值为"+redis.get("t"));
        }
    }




    @Test
    public void test3(){

    }





}
