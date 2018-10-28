package cn.zzh.foreground_client.project.controller;


import cn.zzh.foreground_client.project.entity.*;
import cn.zzh.foreground_client.project.service.*;
import cn.zzh.foreground_client.project.tools.redis.Redis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Description:
 * @auther: 快乐水 青柠可乐
 * @date: 下午4:30 2018/10/7
 * @param:
 * @return:
 *
 */
@RestController
@RequestMapping(value = "/i//user/my")
public class My {
    private final static Logger logger = LoggerFactory.getLogger(My.class);
    private long now=System.currentTimeMillis();

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    BankService bankService;
    @Autowired
    CompactService compactService;
    @Autowired
    IdeaService ideaService;
    @Autowired
    DealService dealService;
    @Autowired
    Tools tools;
    @Autowired
    Redis redis;

    private static final String R_ID_CARD ="idCard";
    private static final String R_CARD ="card";
    private static final String R_REAL_NAME ="realName";
    private static final String R_CARD_TYPE ="cardType";
    private static final String R_PHONE_NUMBER ="phoneNumber";
    private static final String JWT="receptionId";
    private final static long DAY=86400;
    private final static long MOUTH      =        2592000;
    private static final long TEMPORARY =900;




    /** 16
     *1.4.1.1 我的页面第一个接口——获得基本信息
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:15 2018/10/16 
     * @param: [uerId]
     * @return: cn.zzh.foreground_client.project.entity.Result<cn.zzh.foreground_client.project.entity.User>
     *
     */
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result<User> myInfo(@PathVariable long id){

        User user=userService.selectByPrimaryKey(id);
        redis.set(id+R_PHONE_NUMBER,user.getPhoneNumber(),MOUTH);
        return new Result<>(true,user);
    }

    /**17
     *1.4.1.2 我的页面第二个接口——获得未读消息
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:15 2018/10/16 
     * @param: [userId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/msg/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result<Integer> getProducts(@PathVariable("id")long id){
        return new  Result<>(true,messageService.selectCountByUIdRead(id));
    }

    /**18
     *1.4.2.1 实名认证——提交用户表信息
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [realName, id, idCard, card]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/identification/{id}",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> identification(@RequestParam   String realName, @PathVariable("id")long id, @RequestParam  String idCard, @RequestParam  String card){
        //调用第三方接口或者：（暂时不用）


        //判断身份证是否合法
        if (!tools.idCardVertify(idCard)) {
            //身份证非法
            return new Result<>(false,301);
        }
        //判断输入的名字是否符合要求
        if(!tools.chineseVertify(realName)){
            //名字不是中文
            return new Result<>(false,302);
        }
        //判断银行卡是否符合要求
        if(!tools.cardVertify(card)){
            //银行卡不符合要求
            return new Result<>(false,303);
        }
        //判断身份证是否重复(名字可以相同，身份证号码不能重复)
        String identify=userService.selectByPrimaryKey(1L).getIdCard();
        if(idCard.equals(identify)){
            //身份证重复
            return new Result<>(false,304);
        }
        //判断银行卡是否重复
        if(bankService.selectAccountByCard(card)>0){
            //银行卡存在
            return new Result<>(false,305);
        }
        //为输入进行缓存存入
        redis.set(id+R_ID_CARD,idCard,TEMPORARY );
        redis.set(id+R_CARD,card,TEMPORARY );
        redis.set(id+R_REAL_NAME,realName,TEMPORARY );
        return new Result<>(true);
    }
    
    /**19
     *1.4.2.2 实名认证——验证银行卡信息
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [cardType, phoneNumber, id]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/identification/card/{id}",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> identificationCard(@RequestParam String cardType,  @PathVariable("id")long id){
        if(!tools.chineseVertify(cardType)){
            //卡类型输入非中文
            return new Result<>(false,306);
        }
        redis.set(id+R_CARD_TYPE,cardType,TEMPORARY );
        return new Result<>(true);
    }

    /**20
     *1.4.2.3: 实名认证——发送验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, phoneNumber]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/identification/card/msgcode/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result cardMsgcode(@PathVariable("id")long id,@RequestParam String phoneNumber){

//入参校验，是否纯数字和位数是否正确；
        if(tools.pohoneVertify(phoneNumber)){
            //判断电话是否没有被注册
            if(userService.selectExitPhoneNumber(phoneNumber)){
                //判断短信请求验证是否符合要求
                switch (tools.msgCodePermission(phoneNumber)){
                    case 0:
                        tools.sendAliMsg(phoneNumber);
                        return new Result<>(true);
                    case 1:
                        //验证码间隔请求小于60秒
                        return new Result<>(false,307);
                    case 2:
                        //超过当天短信验证请求
                        return new Result<>(false,308);
                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            //电话没注册
            return new Result<>(false,309);
        }
        //手机格式错误
        return new Result<>(false,310);
    }

    /**21
     *1.4.2.4: 实名认证——校验验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, phoneNumber, msgCode, idCard, card, realName, cardType]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/identification/card/verification/{id}",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> cardVertification(@PathVariable("id")long id, @RequestParam String phoneNumber, @RequestParam  String msgCode){
        //校验验证码
        if (tools.msgCodeVertify(msgCode, phoneNumber)) {
            if(redis.hasKey(id+R_ID_CARD)&&redis.hasKey(id+R_REAL_NAME)&&redis.hasKey(id+R_CARD)&&redis.hasKey(id+R_CARD_TYPE)){

                //进行update
                User user=new User();
                logger.info("________R_ID_CARD"+String.valueOf(redis.get(id+R_ID_CARD)));
                user.setId(id);
                user.setIdCard(redis.get(id+R_ID_CARD).toString());
                user.setRealName(redis.get(id+R_REAL_NAME).toString());
                user.setCreatedAt(now);
                user.setUpdatedAt(now);
                userService.updateByPrimaryKeySelective(user);
                Bank bank=new Bank();
                bank.setUserId(id);
                bank.setCard(redis.get(id+R_CARD).toString());
                bank.setType(redis.get(id+R_CARD_TYPE).toString());
                bank.setCreatedAt(now);
                bank.setUserId(id);
                bankService.insertSelective(bank);
                return new Result<Integer>(true);
            }
            //缓存失效
            return new Result<>(false, 312);
        }
        //验证码错误
        return new Result<>(false,311);
    }


    /**22
     *1.4.3.0 更换手机界面，显示用户信息（防止用户信息更新了，前端有的信息没办法进行更新）
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result<cn.zzh.foreground_client.project.entity.User>
     *
     */
    @RequestMapping(value = "/info/phone/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result<User> phoneChange (@PathVariable("id") long id){

        User user = userService.selectByPrimaryKey(id);
        return new Result<>(true,user);
    }

    /**23
     *1.4.3.1: 获取验证码——旧手机
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/phone/msgcode/{id}",method = RequestMethod.GET)
    public  Result phoneMsgcode (@PathVariable("id") long id,@RequestParam String phoneNumber){
        //需要填写code

//入参校验，是否纯数字和位数是否正确；
        if(tools.pohoneVertify(phoneNumber)){
            //判断电话是否没有被注册
            if(userService.selectExitPhoneNumber(phoneNumber)){
                //判断短信请求验证是否符合要求

                switch (tools.msgCodePermission(phoneNumber)){
                    case 0:
                        tools.sendAliMsg(phoneNumber);
                        return new Result<>(true);

                    case 1:
                        //请求间隔小于60秒
                        return new Result<>(false,307);

                    case 2:
                        //请求次数上限
                        return new Result<>(false,308);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            //电话号码不存在
            return new Result<>(false,309);
        }
        //电话号码格式错误
        return new Result<>(false,310);
    }

    /**24
     *1.4.3.2 校验验证码——旧手机
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/phone/msgcode/verification/{id}",method = RequestMethod.POST)
    public  Result<Integer> phoneVerification(@PathVariable("id") long id, @RequestParam String msgCode, @RequestParam String phoneNumber){

        //校验
        if (tools.msgCodeVertify(msgCode, phoneNumber)){
            return new Result<Integer>(true);
        }
        return new Result<>(false,311);

    }

    /**25
     *1.4.3.3 发送验证码——新手机
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, phoneNumber]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/phone/new/msgcode/{id}",method = RequestMethod.GET)
    public  Result phoneNewMsgcode(@PathVariable("id") long id,@RequestParam  String phoneNumber){
        if(tools.pohoneVertify(phoneNumber)){
            //判断电话是否没有被注册
            if(!userService.selectExitPhoneNumber(phoneNumber)){
                //判断短信请求验证是否符合要求

                switch (tools.msgCodePermission(phoneNumber)){
                    case 0:
                        redis.set(id+R_PHONE_NUMBER,phoneNumber,TEMPORARY );
                        tools.sendAliMsg(phoneNumber);
                        return new Result<>(true);
                    case 1:
                        return new Result<>(false,307);

                    case 2:
                        return new Result<>(false,308);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            return new Result<>(false,309);
        }
        return new Result<>(false,310);

    }

    /**26
     *1.4.3.4 校验验证码——新手机
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, msgCode, phoneNumber]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/phone/new/msgcode/verification/{id}",method =RequestMethod.POST)
    public  Result<Integer> phoneNewVerification(@PathVariable("id") long id , @RequestParam String msgCode){
        //校验验证码
        if(!redis.hasKey(id+R_PHONE_NUMBER)){
            return new Result<>(false,312);
        }
        String phoneNumber=redis.get(id+R_PHONE_NUMBER).toString();
        if(tools.msgCodeVertify(msgCode,phoneNumber)){
            userService.updateByPrimaryKeySelective(new User(id,phoneNumber));
            return new Result<>(true);
        }
        return new Result<>(false,311);
    }


    /**27
     *1.4.4.1 修改密码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, oldPassword, firstPassword, secondPassword]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/setting/password/{id}",method =RequestMethod.POST)
    public  Result passwordSetting(@PathVariable("id") long id, @RequestParam String oldPassword, @RequestParam String firstPassword, @RequestParam String secondPassword){
        User user=userService.selectByPrimaryKey(id);
        String salt=user.getSalt();

        if(user.getPhoneNumber().equals(userService.selectPhoneByPwd(tools.md5(oldPassword+salt)))){
            switch (tools.pwdVertify(firstPassword,secondPassword)){
                case 0:
                    String newSalt=tools.uuidGenerator();
                    String pwd=tools.md5(firstPassword+newSalt);
                    User user1=new User();
                    user1.setId(id);
                    user1.setPassword(pwd);
                    user1.setSalt(newSalt);
                    user1.setUpdatedAt(now);
                    userService.updateByPrimaryKeySelective(user1);
                    return new Result<>(true);
                case 1:
                    //密码长度错误
                    return new Result<>(false,313);
                case 2:
                    //两次密码不一致
                    return new Result<>(false,314);
                default:
                    return new Result<>(false);
            }
        }
        //密码错误
        return new Result<>(false,315);
    }

    /**28 需要补充
     *1.4.5.1 退出登录
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/setting/logout/{id}",method =RequestMethod.GET)
    public  Result logout(@PathVariable long id){
        //退出 删除jwt
        redis.del(JWT+id);
        return new Result(true);
    }


    /**29
     *1.4.5.1 点击消息中心，获得我的所有消息
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result<com.sun.tools.javac.util.List<cn.zzh.foreground_client.project.entity.Message>>
     *
     */
    @RequestMapping(value = "/messages/{id}",method =RequestMethod.GET)
    public  Result<java.util.List<Message>> messages(@PathVariable long id){
        java.util.List<Message> messages=messageService.selectByUserIdList(id);
        return new Result<>(true,messages);
    }


    /**30
     *1.4.6.1 我的理财首页——获得我的产品购买记录
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/investment/{id}",method =RequestMethod.GET)
    public  Result<java.util.List<Compact>> myInvestment(@PathVariable  long id){
        java.util.List<Compact> compacts=compactService.selectListByUserId(id);
        return new Result<>(true,compacts);
    }


    /**31
     *1.4.6.2  预约续投
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, compactingId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/investment/book/{id}",method =RequestMethod.POST)
    public  Result investmentBook( @PathVariable long id, @RequestParam  long compactingId){
        //调用接口
        return  new Result(true);
    }


    /**32
     *1.4.6.4 取消续投
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, compactingId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/investment/debook/{id}",method =RequestMethod.POST)
    public  Result investmentDebook(@PathVariable long id, @RequestParam  long compactingId){
        //调用接口
        return new Result(true);
    }


    /**33
     *1.4.7 交易记录
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/transaction/record/{id}",method =RequestMethod.GET)
    public  Result<java.util.List<Deal>> transactionRecord(@PathVariable("id") long id){
        java.util.List<Deal> deals=dealService.selectByUserId(id);
        return new Result<>(true,deals);
    }


    /**34
     *1.4.8.1 获取我的银行卡列表
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result<com.sun.tools.javac.util.List<cn.zzh.foreground_client.project.entity.Bank>>
     *
     */
    @RequestMapping(value = "/bank/{id}",method =RequestMethod.GET)
    public  Result<java.util.List<Bank>> myBank(@PathVariable("id") long id){
        java.util.List<Bank> banks = bankService.selectByUserId(id);

        return new Result<>(true,banks);
    }

    /**35
     *1.4.8.2 添加银行卡——获得用户基本信息
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [userId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/info/{id}",method =RequestMethod.GET)
    public  Result<User> bankMyInfo(@PathVariable long id){
        return new Result<>(true,userService.selectByPrimaryKey(id));
    }

    
    /**36.1 :再补充（需要增加一个接口）
     *1.4.8.3 提交卡号
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, cardId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/{id}",method =RequestMethod.POST)
    public Result bankCard(@PathVariable long id, @RequestParam String card, @RequestParam String cardType){
        if(bankService.selectAccountByCard(card)>0){
            return new Result<>(false,305);
        }
        redis.set(id+R_CARD,card,TEMPORARY );
        redis.set(id+R_CARD_TYPE,cardType,TEMPORARY);
        return new Result(true);
    }

    /**36.2
     * @Description: 提交电话
     * @auther: 快乐水 青柠可乐
     * @date: 下午4:17 2018/10/16
     * @param: [id, card]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/phone/{id}",method =RequestMethod.POST)
    public Result<Integer> bankCardPhone(@PathVariable long id, @RequestParam String phoneNumber){
        //参数校验
        if(!tools.pohoneVertify(phoneNumber)){
            //电话格式错误
            return new Result<>(false,310);
        }
        //电话是否存在
        if(!userService.selectExitPhoneNumber(phoneNumber)){
            //电话不存在
            return new Result<>(false,309);
        }
        //电话放入缓存
        redis.set(id+R_PHONE_NUMBER,phoneNumber,TEMPORARY);
        return new Result<Integer>(true);
    }

    /**36.3
     *
     * @Description: 获得验证码
     * @auther: 快乐水 青柠可乐
     * @date: 下午4:17 2018/10/27
     * @param: [id, card]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/phone/msgcode/{id}",method =RequestMethod.POST)
    public  Result bankCardMsg(@PathVariable long id){
        String phoneNumber=redis.get(id+R_PHONE_NUMBER).toString();
        switch (tools.msgCodePermission(phoneNumber)){
            case 0:
                tools.sendAliMsg(phoneNumber);
                return new Result<>(true);
            case 1:
                //请求间隔小于60秒
                return new Result<>(false,307);

            case 2:
                //请求次数上限
                return new Result<>(false,308);

            default:
                logger.info("请求验证码出现异常有误");
                return new Result<>(false);
        }
    }

    /**36.4
     *
     * @Description: 校验验证码
     * @auther: 快乐水 青柠可乐
     * @date: 下午4:18 2018/10/27
     * @param: [id, card]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/phone/msgcode/verification/{id}",method =RequestMethod.POST)
    public  Result bankCardVertify(@PathVariable long id,@RequestParam String msgCode){
        if(!redis.hasKey(id+R_PHONE_NUMBER)||!redis.hasKey(id+R_CARD_TYPE)||!redis.hasKey(id+R_CARD)){
            return new Result<>(false,312);
        }
        String phoneNumber=redis.get(id+R_PHONE_NUMBER).toString();
        if(tools.msgCodeVertify(msgCode,phoneNumber)){
            Bank bank=new Bank();
            bank.setUserId(id);
            bank.setCard(redis.get(id+R_CARD).toString());
            bank.setType(redis.get(id+R_CARD_TYPE).toString());
            bank.setCreatedAt(now);
            bank.setUpdatedAt(now);
            bankService.insertSelective(bank);
            redis.del(id+R_CARD);
            return new Result<>(true);
        }
        return new Result<>(false,311);
    }


    /**37银行卡解除绑定
     *1.4.9.1 : 获取验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, phoneNumber]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/debook/msgcode/{id}",method =RequestMethod.GET)
    public  Result bankMsgcode(@PathVariable long id,String phoneNumber){
        switch (tools.msgCodePermission(phoneNumber)){
            case 0:
                tools.sendAliMsg(phoneNumber);
                redis.set(id+R_PHONE_NUMBER,phoneNumber,TEMPORARY);
                return new Result<>(true);
            case 1:
                //请求间隔小于60秒
                return new Result<>(false,307);

            case 2:
                //请求次数上限
                return new Result<>(false,308);

            default:
                logger.info("请求验证码出现异常有误");
                return new Result<>(false);
        }
    }


    /**38银行卡解除绑定
     *1.4.9.2: 校验验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, msgCode, card]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/debook/msgcode/verification/{id}",method =RequestMethod.DELETE)
    public  Result bankMsgcodeVertify(@PathVariable("id") long id, @RequestParam  String msgCode, @RequestParam long bankId){
        if (!redis.hasKey(id+R_PHONE_NUMBER)){
            //输入超时
            return new Result<>(false,312);
        }
        if (bankService.selectUserIdByBankId(bankId)==id){
            if(tools.msgCodeVertify(msgCode,redis.get(id+R_PHONE_NUMBER).toString())){
                bankService.deleteByPrimaryKey(bankId);
                return new Result<>(true);
            }
            return new Result<>(false,311);
        }
        //该银行卡非该用户所有
        return new Result<>(false,316);


    }


    /**39
     *1.5.1: 提交意见
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, content]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/idea/{id}",method =RequestMethod.POST)
    public  Result myIdea(@PathVariable long id,@RequestParam String content){
        Idea idea=new Idea();
        idea.setUserId(id);
        idea.setContent(content);
        idea.setCreatedAt(now);
        idea.setUpdatedAt(now);
        ideaService.insertSelective(idea);
        return new Result(true);
    }

}
