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
@RequestMapping(value = "/user/my")
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

    private static final String RIdCard="idCard";
    private static final String Rcard="card";
    private static final String RRealName="realName";
    private static final String RCardType="cardType";
    private static final long Temporary=900;




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
    public Result<User> myInfo(@PathVariable("id")long uerId){

        return new Result<>(true,userService.selectByPrimaryKey(uerId));
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
        redis.set(id+RIdCard,idCard,Temporary);
        redis.set(id+Rcard,card,Temporary);
        redis.set(id+RRealName,realName,Temporary);
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
    public Result<Integer> identificationCard(@RequestParam String cardType, @RequestParam  String phoneNumber, @PathVariable("id")long id){
        if(!tools.chineseVertify(cardType)){
            //卡类型输入非中文
            return new Result<>(false,306);
        }
        redis.set(id+RCardType,cardType,Temporary);
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
            if(redis.hasKey(id+RIdCard)&&redis.hasKey(id+RRealName)&&redis.hasKey(id+Rcard)&&redis.hasKey(id+RCardType)){

                //进行update
                User user=new User();
                logger.info("________RIdCard"+String.valueOf(redis.get(id+RIdCard)));
                user.setId(id);
                user.setIdCard(redis.get(id+RIdCard).toString());
                user.setRealName(redis.get(id+RRealName).toString());
                user.setCreatedAt(now);
                user.setUpdatedAt(now);
                userService.updateByPrimaryKeySelective(user);
                Bank bank=new Bank();
                bank.setUserId(id);
                bank.setCard(redis.get(id+Rcard).toString());
                bank.setType(redis.get(id+RCardType).toString());
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
    private Result<User> phoneChange (@PathVariable("id") long id){

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
    private Result phoneMsgcode (@PathVariable("id") long id,@RequestParam String phoneNumber){
        //需要填写code





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
                        return new Result<>(false,23);

                    case 2:
                        return new Result<>(false,23);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            return new Result<>(false,23);
        }
        return new Result<>(false,23);
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
    private Result phoneVerification(@PathVariable("id") long id,@RequestParam String msgCode,@RequestParam String phoneNumber){
        //需要code码





        //校验
        if (tools.msgCodeVertify(msgCode, phoneNumber)){
            return new Result(true);
        }
        return new Result(false);

    }

    /**25
     *1.4.3.3 发送验证码——新手机
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, newPhoneNumber]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/info/phone/new/msgcode/{id}",method = RequestMethod.GET)
    private Result phoneNewMsgcode(@PathVariable("id") long id,@RequestParam  String newPhoneNumber){
        if(tools.pohoneVertify(newPhoneNumber)){
            //判断电话是否没有被注册
            if(!userService.selectExitPhoneNumber(newPhoneNumber)){
                //判断短信请求验证是否符合要求

                switch (tools.msgCodePermission(newPhoneNumber)){
                    case 0:
                        tools.sendAliMsg(newPhoneNumber);
                        return new Result<>(true);

                    case 1:
                        return new Result<>(false,25);

                    case 2:
                        return new Result<>(false,25);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            return new Result<>(false,25);
        }
        return new Result<>(false,25);

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
    private Result phoneNewVerification(@PathVariable("id") long id ,@RequestParam String msgCode,String phoneNumber){
        //校验验证码
        if(tools.msgCodeVertify(msgCode,phoneNumber)){
            userService.updateByPhoneNumberSelective(new User(phoneNumber));
            return new Result(true);
        }
        return new Result(true);
    }


    /**27
     *1.4.4.1 修改密码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:16 2018/10/16 
     * @param: [id, oldPassword, newPassword, comfirmPassword]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/setting/password/{id}",method =RequestMethod.POST)
    private Result passwordSetting(@PathVariable("id") long id, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String comfirmPassword){
        User user=userService.selectByPrimaryKey(id);
        String salt=user.getSalt();
        if(tools.md5(oldPassword + salt).equals(user.getPassword())){
            switch (tools.pwdVertify(newPassword,comfirmPassword)){
                case 0:
                    String newsalt=tools.uuidGenerator();
                    String pwd=tools.md5(newPassword+salt);
                    User user1=new User();
                    user.setId(id);
                    user.setPassword(pwd);
                    user.setSalt(salt);
                    user.setUpdatedAt(now);
                    userService.updateByPrimaryKeySelective(user);
                    return new Result<>(true);
                case 1:
                    return new Result<>(false,105);
                case 2:
                    return new Result<>(false,106);
                default:
                    return new Result<>(false);
            }
        }
        return new Result<>(false,27);
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
    private Result logout(@PathVariable("id") long id){


        //退出 删除jwt
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
    private Result<java.util.List<Message>> messages(@PathVariable("id")long id){
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
    private Result myInvestment(@PathVariable("id") long id){
        java.util.List<Compact> compacts=compactService.selectListByUserId(id);
        return new Result(true);
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
    @RequestMapping(value = "/investment/book/{id},compactingId={compactingId}",method =RequestMethod.GET)
    private Result investmentBook( @PathVariable("id") long id,  long compactingId){
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
    @RequestMapping(value = "/investment/debook/{id},compactingId={compactingId}",method =RequestMethod.GET)
    private Result investmentDebook(@PathVariable("id") long id,  long compactingId){
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
    private Result<java.util.List<Deal>> transactionRecord(@PathVariable("id") long id){
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
    private Result<java.util.List<Bank>> myBank(@PathVariable("id") long id){
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
    private Result<User> bankMyInfo(@PathVariable("id") long userId){

        return new Result<>(true,userService.selectByPrimaryKey(userId));
    }

    
    /**36 :再补充（需要增加一个接口）
     *1.4.8.3 提交卡号
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午11:17 2018/10/16 
     * @param: [id, cardId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/bank/card/{id}",method =RequestMethod.POST)
    private Result bankCard(@PathVariable("id")long id,  long cardId){
        //update
        return new Result(true);
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
    @RequestMapping(value = "/bank/card/msgcode/{id}",method =RequestMethod.GET)
    private Result bankMsgcode(@PathVariable("id") long id,@RequestParam String phoneNumber){
        //短信请求同
        return new Result(true);
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
    @RequestMapping(value = "/bank/card/msgcode/{id}",method =RequestMethod.DELETE)
    private Result bankMsgcodeVertify(@PathVariable("id") long id, @RequestParam      String msgCode, @RequestParam String card){
        //短信验证同
        return new Result(true);
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
    private Result myIdea(@PathVariable("id") long id,@RequestParam String content){
        Idea idea=new Idea();
        idea.setId(id);
        idea.setContent(content);
        idea.setCreatedAt(now);
        idea.setUpdatedAt(now);
        ideaService.insertSelective(idea);
        return new Result(true);
    }

}
