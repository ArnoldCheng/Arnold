package cn.zzh.foreground_client.project.service;

import cn.zzh.foreground_client.project.entity.Product;
import org.apache.ibatis.annotations.ResultMap;


import java.util.List;


public interface ProductService {

    /**
     * 查询上架的产品，会是多个，所以返回List
     * @param isShelves 1-上架 0-不上架
     * @return List<Product>
     */

    List<Product> selectByIsShelves(Integer isShelves);


    /**.
     * 查询鼎力推荐的产品，只有一个
     * @param status 1-推荐  0-不推荐
     * @return Product
     */

    Product selectByStatus(Integer status);

    /**.
     * 查询所有产品
     * @return List
     */

    List<Product> selectAll();


    /**：
     * 根据Id查询对应的产品
     * @param id id
     * @return Product
     */

    @ResultMap("ResultMapWithBLOBs")
    Product selectById(Long id);


}