package cn.zzh.foreground_client.project.tools.normal;


import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午8:13 2018/10/14
 * @Modified By:
 */
@Component
public class Phone {
    private static final Integer SIZE=11;
    public boolean vertifySize(String phoneNumber){
        System.out.println("vertifySize:           "+phoneNumber.length());
        return phoneNumber.length() == SIZE;
    }

    public boolean isNumber(String phoneNumber){
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(phoneNumber).toString();
        } catch (Exception e) {
            return false;
        }
        // matcher是全匹配
        Matcher isNum = pattern.matcher(bigStr);
        System.out.println("isNumber :           "+isNum.matches());
        return isNum.matches();
    }
}

