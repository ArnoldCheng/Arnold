package cn.zzh.foreground_client.project.tools.security;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午4:45 2018/10/7
 * @Modified By:
 */
@Component
public class RandomCode {
    public String getRandomCode(int length){
        String str="1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number =random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}


