package cn.zzh.foreground_client.project.controller;

import cn.zzh.foreground_client.project.entity.Result;
import cn.zzh.foreground_client.project.entity.User;
import cn.zzh.foreground_client.project.service.Tools;
import cn.zzh.foreground_client.project.service.UserService;
import cn.zzh.foreground_client.project.tools.security.JwtTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Description:
 * @auther: 快乐水 青柠可乐
 * @date: 下午4:31 2018/10/7
 * @param:
 * @return:
 *
 */
@RestController
@RequestMapping(value = "/user")
public class RegisterAndLogin {
    private final static Logger logger = LoggerFactory.getLogger(RegisterAndLogin.class);

    @Autowired
    private UserService userService;
    @Autowired
    private Tools tools;


    /**1：注册-请求验证码：
     *1.1.1.1
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 下午3:03 2018/10/15
     * @param: [phoneNumber]
     * @return: 101：手机格式错误；102：手机号已经注册；103 ：验证码已经发送 104：当日请求已经达到上限
     * cn.zzh.foreground_client.project.entity.Result<java.lang.Integer>
     *
     */
    @GetMapping(value = "/register/msgcode",produces =  "application/json;charset=UTF-8")
    public Result<Integer> registerGetMsgcode(@RequestParam String phoneNumber){
        logger.info("com.controller.RegisetAndLogin.registerGetMsgcode ： phoneNumber: "+phoneNumber);
        //入参校验，是否纯数字和位数是否正确；
        if(tools.pohoneVertify(phoneNumber)){
            //判断电话是否没有被注册
            if(!userService.selectExitPhoneNumber(phoneNumber)){
                //判断短信请求验证是否符合要求
                switch (tools.msgCodePermission(phoneNumber)){
                    case 0:
                        tools.sendAliMsg(phoneNumber);
                        return new Result<>(true);
                    case 1:
                        return new Result<>(false,103);

                    case 2:
                        return new Result<>(false,104);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            return new Result<>(false,102);
        }
        return new Result<>(false,101);
    }

    /**2：校验验证码
     *1.1.1.2
     * @Description: 校验验证码： 0 成功  1验证码错误 2图片验证码错误 3：其它（图片验证码还没加）
     * @auther: 快乐水 青柠可乐
     * @date: 下午2:55 2018/10/15
     * @param: [msgCode, phoneNumber, picCode]
     * @return: cn.zzh.foreground_client.project.entity.Result<java.lang.Integer>
     *
     */
    @PostMapping(value = "/register/msgcode",produces =  "application/json;charset=UTF-8")
    public Result<Integer> registerPostNext(@RequestParam(value = "msgCode") String msgCode, @RequestParam String phoneNumber, @RequestParam(required = false)String picCode ) {
        if (tools.msgCodeVertify(msgCode, phoneNumber)) {
                return new Result<Integer>(true);
        }
        return new Result<>(false, 120);
    }

    /**3：设置密码
     *1.1.1.3 注册——验证密码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午1:30 2018/10/16
     * @param: [phoneNumber, firstPassword, secondPassword]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/register/password",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> registerPostPassword(@RequestParam String phoneNumber, @RequestParam  String firstPassword, @RequestParam  String secondPassword){
        switch (tools.pwdVertify(firstPassword,secondPassword))
        {
            case 0:
                String salt=tools.uuidGenerator();
                String pwd=tools.md5(firstPassword+salt);
                userService.insertSelective(new User(phoneNumber,pwd,salt));
                return new Result<>(true);
            case 1:
                return new Result<>(false,105);
            case 2:
                return new Result<>(false,106);
            default:
                return new Result<>(false);
        }
    }

    /**4:登录
     *1.1.2.1 登录
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午2:04 2018/10/16
     * @param: [phoneNumber, password]
     * @return: cn.zzh.foreground_client.project.entity.Result<cn.zzh.foreground_client.project.entity.User>
     *
     */
    @RequestMapping(value = "login/account",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> loginAccount(@RequestParam   String phoneNumber, @RequestParam String password){
        if (!tools.pohoneVertify(phoneNumber)){
            return new Result<>(false,107);
        }
        User user=userService.selectSelective(new User(phoneNumber));
        logger.info("___________________________________"+String.valueOf(user));
        if(user==null){
            return new Result<>(false,110);
        }
        String pwd=tools.md5(password+user.getSalt());
        logger.info(user.getSalt());
        logger.info(pwd);
        if(user.getIsLocked().equals(1)){
            return new Result<>(false,109);
        }
        if (phoneNumber.equals(userService.selectPhoneByPwd(pwd))){
            user=userService.selectByPrimaryKey(user.getId());
            try {
                JwtTool.createToken(user.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Result<>(true);
        }
        return new Result<>(false,108);
    }

    /**5:忘记密码
     *1.1.3.1 忘记密码：找回密码——获取验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午2:24 2018/10/16
     * @param: [phoneNumber]
     * @return:
     * 111：手机未注册； 112：短信已经发送； 113：今天发送已达到上限 114：手机号格式错误，请重试
     * cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "reset/msgcode",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result resetGetMsgcode(@RequestParam   String phoneNumber){
        //入参校验，是否纯数字和位数是否正确；
        if(tools.pohoneVertify(phoneNumber)){
            //判断电话是否存在
            if(userService.selectExitPhoneNumber(phoneNumber)){
                //判断短信请求验证是否符合要求
                switch (tools.msgCodePermission(phoneNumber)){
                    case 0:
                        tools.sendAliMsg(phoneNumber);
                        return new Result<>(true);

                    case 1:
                        return new Result<>(false,112);

                    case 2:
                        return new Result<>(false,113);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            return new Result<>(false,111);
        }
        return new Result<>(false,114);
    }


    /**6：忘记密码：验证码校验
     *1.1.3.2  下一步（校验验证码）
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午2:39 2018/10/16
     * @param: [phoneNumber, msgCOde]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "reset/msgcode",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> resetPostMsgcode(@RequestParam String phoneNumber, @RequestParam   String msgCode){
        if (!tools.pohoneVertify(phoneNumber)){
            return new Result<>(false,115);
        }
        if(!userService.selectExitPhoneNumber(phoneNumber)){
            return new Result<>(false,116);
        }
        if (tools.msgCodeVertify(msgCode,phoneNumber)){
            return new Result<>(false,117);
        }
        return new Result<>(true);
    }

    /**7:重置密码
     *1.1.3.3 确认（提交新密码）
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午2:50 2018/10/16
     * @param: [phoneNumber, firstPassword, secondPassword]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "reset/password",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result resetPostPassword(@RequestParam  String phoneNumber, @RequestParam  String firstPassword, @RequestParam(value ="secondPassword") String secondPassword){
        switch (tools.pwdVertify(firstPassword,secondPassword)){
            case 0:
                String salt=tools.uuidGenerator();
                String pwd=tools.md5(firstPassword+salt);
                userService.updateByPhoneNumberSelective(new User(phoneNumber,pwd,salt));
                return new Result<>(true);
            case 1:
                return new Result<>(false,118);
            case 2:
                return new Result<>(false,119);
            default:
                return new Result<>(false);
        }

    }


}
