package cn.zzh.foreground_client.project.controller;

import cn.zzh.foreground_client.project.entity.Banner;
import cn.zzh.foreground_client.project.entity.Product;
import cn.zzh.foreground_client.project.entity.Result;
import cn.zzh.foreground_client.project.service.BannerService;
import cn.zzh.foreground_client.project.service.ProductService;
import cn.zzh.foreground_client.project.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class UserHome {

    @Autowired
    private UserService userService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ProductService productService;

    /**8: 首页 1
     *1.2.1 首页第一个接口——banner图
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午9:14 2018/10/16
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result<java.util.List<cn.zzh.foreground_client.project.entity.Banner>>
     *
     */
    @RequestMapping(value = "/home/banner", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<List<Banner>> homeGetBanner(@RequestParam(value = "id", required = false) Long id) {
        return new Result<>(true,bannerService.selectByStatus(1));
    }
    /**9：首页 2
     *
     * @Description:
     * @auther: 快乐水 青柠可乐
     * @date: 上午9:19 2018/10/16
     * @param: [id]
     * @return: cn.zzh.foreground_client.project.entity.Result<cn.zzh.foreground_client.project.entity.Product>
     *
     */
    @RequestMapping(value = "/home/recommendation", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Result<Product> homeGetRecommandation(@RequestParam(value = "id", required = false) Long id, @RequestParam(required = false) Byte isNoob) {
        if (id==null|| isNoob==null){
            return new Result<>(true,productService.selectById(1L));
        }
        if (isNoob.equals(1)){
            return new Result<>(true,productService.selectByStatus(1));
        }
        return new Result<>(true,productService.selectById(1L));
    }
}
