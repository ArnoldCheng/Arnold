package cn.zzh.foreground_client.project.service.impl;

import cn.zzh.foreground_client.project.dao.ProductMapper;
import cn.zzh.foreground_client.project.entity.Product;
import cn.zzh.foreground_client.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 快乐水 青柠可乐
 * @Description:
 * @Date: Created in 下午3:37 2018/10/13
 * @Modified By:
 */
@Service
public class ProductServiceImpl  implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Override
    public List<Product> selectByIsShelves(Integer isShelves) {
        return productMapper.selectByIsShelves(isShelves);
    }

    @Override
    public Product selectByStatus(Integer status) {
        return productMapper.selectByStatus(status);
    }

    @Override
    public List<Product> selectAll() {
        return productMapper.selectAll();
    }

    @Override
    public Product selectById(Long id) {
        return productMapper.selectById(id);
    }
}
