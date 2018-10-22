package cn.zzh.foreground_client.project.service;


/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午4:33 2018/10/7
 * @Modified By:
 */

public interface Tools  {
    /**:
     * 设置了4个缓存：
     * 1：每次请求的起始时间 key+THETIME
     * 2：第一次请求的起始时间 key+FIRSTTIME
     * 3：当天请求的次数 key+AMOUNT
     * 4：该次请求的验证码 key+MSG
     * 直接调用阿里巴巴的短信API发送短信，验证码和相关的缓存设置已经封装好了; 直接输入电话号码就可以了
     * @param phoneNumber phoneNumber
     */
    void sendAliMsg(String phoneNumber);

    /**:
     * 此为普通的MD5加密，需要将盐和密码一起输入，方便对盐值进行保存；
     * @param input input
     * @return String
     */
    String md5(String input);


    /**:
     * 验证电话格式： 位数和字符串包含对值是否为纯数字
     * @param phoneNumber  phoneNumber
     * @return
     */
    boolean pohoneVertify(String phoneNumber);

    /**:
     * 获得发送短信验证码的许可： 输入一个电话，判断是否可以获得发送验证码的许可：判断是否符合：请求次数限制，执行错误次数限制，单次请求间隔；
     * @param phoneNumber phoneNumber
     * @return int:
     * 0: 表示符合获取；有没设置缓存和其它；
     * 1: 表示单次请求间隔小于60S(短信已经发送)
     * 2: 表示当天请求次数超过20次
     * 3: 表示验证码输入错误次数超过3次
     */
    int msgCodePermission(String phoneNumber);


    /**:
     * 验证Post的短信信息，0 全部正确 1验证码错误 （2，3 待定） 2图片验证码错误 3：。。
     * @param msgCode msgCode
     * @param phoneNumber phoneNUmber
     * @return int
     */
    boolean msgCodeVertify(String msgCode,String phoneNumber);

    /**
     *
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午1:43 2018/10/16
     * @param: [first, second]
     * @return: int    0:密码符合规范 1:长度错误，2：密码不一致
     *
     */
    int pwdVertify(String first,String second);

    boolean chineseVertify(String input);

    boolean idCardVertify(String input);

    boolean cardVertify(String input);

    String uuidGenerator();






}
