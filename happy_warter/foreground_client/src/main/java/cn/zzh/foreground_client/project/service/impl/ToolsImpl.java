package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.service.Tools;
import cn.zzh.foreground_client.project.tools.aLiAPI.AliMessage;
import cn.zzh.foreground_client.project.tools.encryption.MD5Util;
import cn.zzh.foreground_client.project.tools.normal.Phone;
import cn.zzh.foreground_client.project.tools.redis.Redis;
import cn.zzh.foreground_client.project.tools.security.RandomCode;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午4:36 2018/10/7
 * @Modified By:
 */
@Service
public class ToolsImpl implements Tools {
    private final static Logger logger = LoggerFactory.getLogger(ToolsImpl.class);
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
    private int times;


    @Autowired
    private Redis redis;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AliMessage aliMessage;
    @Autowired
    private MD5Util md5Util;
    @Autowired
    private RandomCode randomCode;
    @Autowired
    private Phone phone;
    public static String getMSG() {
        return MSG;
    }

    @Override
    public void sendAliMsg(String phoneNumber)  {
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        logger.info("com.service.impl.toolImpl.sendAliMsg :"+phoneNumber);
        String msCode=randomCode.getRandomCode(6);
        //设置单次请求的短信验证码的值；
        redis.set(phoneNumber+MSG,msCode,TEMPORARY);
        logger.info("_________"+phoneNumber+MSG+" is "+redis.get(phoneNumber+MSG));
        //设置改次请求的短信验证码的起始时间；
        redis.set(phoneNumber+THETIME,totalSeconds,TEMPORARY);
        logger.info("_________" +phoneNumber+THETIME+" is "+redis.get(phoneNumber+THETIME));
        //判断该用户是否有缓存
        if(redis.hasKey(phoneNumber+AMOUNT)&&redis.hasKey(phoneNumber+FIRSTTIME)){
            //累加该用户当日请求的次数；
            redis.increment(phoneNumber+AMOUNT,1);
            logger.info("_________"+phoneNumber+AMOUNT+"的缓存是"+redis.get(phoneNumber+AMOUNT));
        }else {
            //设置当天第一次请求缓存的起始时间,设置有效期为24小时
            redis.set(phoneNumber+FIRSTTIME,totalSeconds,DAY);
            logger.info("_________"+phoneNumber+FIRSTTIME+"的缓存是"+redis.get(phoneNumber+FIRSTTIME));
            //设置请求次数累加；
            redis.set(phoneNumber+AMOUNT,1,DAY);
            logger.info("_________"+phoneNumber+AMOUNT+"的缓存是"+redis.get(phoneNumber+AMOUNT));
        }
        try {
            //发送短信验证码；
            aliMessage.sendSms(phoneNumber,msCode);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int msgCodePermission(String phoneNumber) {
        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        //判断是否请求过验证码
        if(redis.hasKey(phoneNumber+FIRSTTIME)){
            long theTime;
            //当前请求过，获得每次请求验证码的间隔时间
            if (redis.hasKey(phoneNumber+THETIME)){
                theTime= Long.parseLong(redis.get(phoneNumber+THETIME).toString());
            }else {
                theTime= 0L;
            }

            logger.info("________currentTime       "+totalMilliSeconds);
            logger.info("________totalSeconds      "+totalSeconds);
            logger.info("________theTIme           "+theTime);

            long intervalTime=totalSeconds - theTime;
            logger.info("________intervalTime : "+intervalTime);
            //获取用户当天请求验证码的次数
            times=(Integer)redis.get(phoneNumber+AMOUNT);
            logger.info("________times "+times);
            //判断是否请求间隔大于60S
            if (intervalTime>MIN){
                //判断是否超过一天请求的最大次数
                if (times<MAXTIMES){
                    //请求间隔大于60秒且没超过当天最大请求次数
                    return 0;
                }else {
                    //超过最大请求次数，返回错误
                    return 2;
                }
            }
            //请求时间小于60秒，返回错误；
            return 1;
        }
        //没请求过，允许请求
        return 0;
    }

    @Override
    public String md5(String input) {
        return md5Util.MD5(input);
    }

    @Override
    public boolean pohoneVertify(String phoneNumber) {
        return phone.vertifySize(phoneNumber) && phone.isNumber(phoneNumber);
    }



    @Override
    public boolean msgCodeVertify(String msgCode, String phoneNumber) {
        return msgCode.equals(redis.get(phoneNumber + MSG));
    }

    @Override
    public int pwdVertify(String first, String second) {
        if(first.equals(second)){
            String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(first);
            boolean rs=matcher.matches();
            if(rs){
                return 0;
            }
            return 1;
        }
        return 2;
    }

    @Override
    public boolean chineseVertify(String input) {
        String regex = "^[\\u4e00-\\u9fa5]*$";
        Matcher m = Pattern.compile(regex).matcher(input);
        return m.find();
    }

    @Override
    public boolean idCardVertify(String input) {
        String regex1="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        Matcher m1 = Pattern.compile(regex1).matcher(input);

        String regex2="^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";
        Matcher m2 = Pattern.compile(regex2).matcher(input);
        return  m1.find() || m2.find();

    }

    @Override
    public boolean cardVertify(String input) {
        String regex="^(\\d{16}|\\d{19})$";
        Matcher m1 = Pattern.compile(regex).matcher(input);
        return m1.find();
    }

    @Override
    public String uuidGenerator() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }



}
