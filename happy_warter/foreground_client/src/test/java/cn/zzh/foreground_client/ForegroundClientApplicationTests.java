package cn.zzh.foreground_client;

import cn.zzh.foreground_client.project.dao.*;
import cn.zzh.foreground_client.project.entity.Deal;
import cn.zzh.foreground_client.project.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ForegroundClientApplicationTests {
    private final static Logger logger = LoggerFactory.getLogger(ForegroundClientApplicationTests.class);
    private Long now=System.currentTimeMillis();

    @Autowired
    ProductMapper productMapper;
    @Autowired
    DealMapper dealMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    CompactMapper compactMapper;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    IdeaMapper ideaMapper;
    @Autowired
    BankMapper bankMapper;

    @Test
    public void bannerTest(){
        logger.info(String.valueOf(bannerMapper.selectByStatus(1)));
    }

    @Test
    public void dealTest(){
        Deal deal=new Deal();
        deal.setProductId(1L);
        deal.setCreatedAt(System.currentTimeMillis());
        deal.setUserId(1L);
        deal.setDealingType(10);
        deal.setProfit(BigDecimal.valueOf(10));
        deal.setInvestment(BigDecimal.valueOf(1000));
        logger.info(String.valueOf(dealMapper.selectById(1L)));
        logger.info(String.valueOf(dealMapper.insertSelective(deal)));
        logger.info(String.valueOf(dealMapper.selectByUserId(1L)));
    }

    @Test
    public void UserTest(){
        User user=new User();
        user.setPhoneNumber("13824429757");
        logger.info(String.valueOf(userMapper.selectSelective(new User("13824429757"))));
        logger.info(String.valueOf(userMapper.selectSelective(user)));
        logger.info(String.valueOf(userMapper.selectByPrimaryKey(1L)));
    }

    @Test
    public void CompactTest(){
        logger.info(String.valueOf(compactMapper.selectListByProductId(1L)));
    }

    @Test
    public void productTest(){
//        logger.info(String.valueOf(productMapper.selectByIsShelves(1)));
        logger.info(String.valueOf(productMapper.selectById(1L)));
    }

    @Test
    public void  userMapperTest(){
        logger.info(String.valueOf(userMapper.selectByPrimaryKey(1L)));
    }

    @Test
    public void BankMapperTest(){
        logger.info(String.valueOf(bankMapper.selectAccountByCard("1")));
    }
}
