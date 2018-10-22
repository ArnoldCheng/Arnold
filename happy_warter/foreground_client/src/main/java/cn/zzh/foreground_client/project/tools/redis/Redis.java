package cn.zzh.foreground_client.project.tools.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午9:20 2018/10/14
 * @Modified By:
 */
@Component
public class Redis {
    private static final String MSG="msg";
    private static final String THETIME="msgTheTime";
    private static final String AMOUNT="amount";
    private static final String ERRORS="errors";
    private static final String FIRSTTIME="firstTIme";
    private final static int    MAXTIMES=20;


    private final static long S          =              1;
    private final static long MIN        =             60;
    private final static long HOUR       =           3600;
    private final static long DAY        =          86400;
    private final static long MOUTH      =        2592000;

    private final static long TEMPORARY  =MIN *15;
    private long ranTime=(long)(15*DAY +Math.random()*(30*DAY -15*DAY +1));

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Redis redis;

    private long totalMilliSeconds = System.currentTimeMillis();
    private long totalSeconds = totalMilliSeconds / 1000;


    /**:
     * 通过key获得对象
     * @param key key
     * @return Object
     */
    public Object get(String key) {
        return  redisTemplate.opsForValue().get(key);
    }


    /**:
     * key_value的形式保存缓存，同时设定时间和描述
     * @param key key
     * @param value 缓存的值，Object类型
     * @param time 保存时间，单位是秒
     */
    public  void set(String key,Object value,long time){
        redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
    }

    /**：
     * 通过key 删除缓存
     * @param key key
     */
    public  void del(String key){
        redisTemplate.delete(key);
    }

    /**：
     * 查找对应的key是否存在缓存中
     * @param key key
     * @return true or false
     */
    public  boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void flush(){

    }

    /**：
     * 返回该用户第一次请求的时间
     * @param key key
     * @return 该用户第一次请求的时间
     */
    public Long firstRequireTime(String key){
        return (Long) redisTemplate.opsForValue().get(key+FIRSTTIME);
    }

    /**：
     * 返回该用户累计次数和错误缓存的剩余有效时间
     * @param firstTime :firstTime
     * @return Long:该用户的当天累计剩余时间
     */
    public Long auxiliaryRemainTime(Long firstTime){
         return firstTime+DAY-totalSeconds;
    }


    public void increment(String key,long l){
        redisTemplate.opsForValue().increment(key,l);
    }
}
