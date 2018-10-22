package cn.zzh.foreground_client.project.controller;
import cn.zzh.foreground_client.project.entity.Bank;
import cn.zzh.foreground_client.project.entity.Product;
import cn.zzh.foreground_client.project.entity.Result;
import cn.zzh.foreground_client.project.entity.User;
import cn.zzh.foreground_client.project.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;


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
public class Financial {
    private final static Logger logger = LoggerFactory.getLogger(Financial.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private CompactService compactService;
    @Autowired
    private BankService bankService;
    @Autowired
    private Tools tools;
    @Autowired
    private UserService userService;


    /**10
     *1.3.1 查看理财产品
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午10:33 2018/10/16
     * @param: [id, isNoob]
     * @return: cn.zzh.foreground_client.project.entity.Result<java.util.List<cn.zzh.foreground_client.project.entity.Product>>
     *
     */
    @RequestMapping(value = "/products/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result<List<Product>> getProducts(@PathVariable(required = false) Long id, @RequestParam(required = false) Byte isNoob) {
        List<Product> products=productService.selectByIsShelves(1);
        if (id==null||isNoob==null){
            return  new Result<>(true,products);
        }
        if(isNoob==1){
            return  new Result<>(true,products);
        }
        for (int i=0;i<products.size();i++){
            if (products.get(i).getId().equals(1L)){
                products.remove(i);
            }
        }
        return  new Result<>(true,products);
    }

    /**11:
     *1.3.1.2 获得某个产品的交易记录
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午10:40 2018/10/16
     * @param: [id, productId]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/products/records/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result<List<cn.zzh.foreground_client.project.entity.Compact>> productsRecords
    (@PathVariable(value = "id") long id, @RequestParam long productId){
            return new  Result<>(true,compactService.selectListByProductId(productId));
    }

    /**12:
     *1.3.2.1立即购买接口——获得用户银行卡列表
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午10:40 2018/10/16
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result<java.util.List<cn.zzh.foreground_client.project.entity.Bank>>
     *
     */
    @RequestMapping(value = "/products/investment/{id}",method = RequestMethod.GET,produces =  "application/json;charset=UTF-8")
    public Result<List<Bank>> productsInvestment(@PathVariable("id") long id){
        return new Result<>(true,bankService.selectByUserId(id));
    }

    /**13:
     *1.3.2.2确认购买——（提交金额和支付方式
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午10:40 2018/10/16
     * @param: [userId, productId, moneyAmount, paymentWay]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/products/determination/{id}",method = RequestMethod.POST,produces =  "application/json;charset=UTF-8")
    public Result<Integer> productsDetermination
    (@PathVariable("id") long userId, @RequestParam long productId, @RequestParam BigDecimal moneyAmount,@RequestParam String paymentWay){
        //需要code



        //判断是否符合产品金额
        Product product=productService.selectById(productId);
        //判断是否超过最低购买金额 -1小于，0等于，1大于；
        int i=moneyAmount.compareTo(product.getMinAmount());
         //判断是否超过最高购买金额
        int j=moneyAmount.compareTo(product.getMaxAmount());
        //判断金额是否符合要求
        if(i<0){
            //不符合产品最大最小金额
            return new Result<>(false,201);
        }
        if (j>0){
            return new Result<>(false,202);
        }
        //判断是否超过用户余额
        User user=userService.selectByPrimaryKey(userId);
        int k=moneyAmount.compareTo(user.getBalance());
        if (k>0){
            //金额超过用户余额
            return new Result<>(false,203);
        }
        //满足条件，可以进行调用；
        return new Result<Integer>(true);
    }

    /**14:
     *1.3.2.3:确认支付——获取验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午10:40 2018/10/16
     * @param: [userId, phoneNumber]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/products/payment/msgcode/{id}",method = RequestMethod.GET)
    public Result productPaymentMsgcode(@PathVariable("id")long userId,@RequestParam   String phoneNumber){
        //需要code


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
                        return new Result<>(false,204);

                    case 2:
                        return new Result<>(false,205);

                    default:
                        logger.info("请求验证码出现异常有误");
                        return new Result<>(false);
                }
            }
            return new Result<>(false,206);
        }
        return new Result<>(false,207);
    }

    /**15:
     *1.3.2.4确认支付——校验验证码
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午10:40 2018/10/16
     * @param: [id, phoneNumber, msgcode]
     * @return: cn.zzh.foreground_client.project.entity.Result
     *
     */
    @RequestMapping(value = "/products/payment/{id}",method = RequestMethod.POST)
    public Result<Integer> productPayment(@PathVariable("id")long id, @RequestParam  String phoneNumber, @RequestParam String msgCode,@RequestParam BigDecimal moneyAmount,@RequestParam String paymentWay){

        if (tools.msgCodeVertify(msgCode, phoneNumber)) {
            //验证码正确,调用接口，进行输入参数操作

            return new Result<Integer>(true);
        }
        //验证码错误
        return new Result<>(false,1);
    }
}
