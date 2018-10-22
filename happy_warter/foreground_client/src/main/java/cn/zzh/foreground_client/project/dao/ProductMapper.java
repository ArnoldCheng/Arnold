package cn.zzh.foreground_client.project.dao;
import cn.zzh.foreground_client.project.entity.Product;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import java.util.List;


public interface ProductMapper {

    /**
     * 查询上架的产品，会是多个，所以返回List
     * @param isShelves 1-上架 0-不上架
     * @return List<Product>
     */
    @Select({
            "select " ,
            "id,serial_id,product_name,product_type,pro_profit,investment_cycle,",
            "min_amount,max_amount ,repayment_way,status,is_shelves,product_intro,",
            "more_information,thumnail ",
            "from product where is_shelves=#{s_shelves}"
    })
    @ResultMap("NormalProduct")
    List<Product> selectByIsShelves(Integer isShelves);


    /**.
     * 查询鼎力推荐的产品，只有一个
     * @param status 1-推荐  0-不推荐
     * @return Product
     */
    @Select({
            "select * from product where status=#{status} order by id limit 1"
    })
    Product selectByStatus(Integer status);


    /**.
     * 查询所有产品
     * @return List
     */
    @Select({
            "select * from product"
    })
    List<Product> selectAll();


    /**：
     * 根据Id查询对应的产品
     * @param id id
     * @return Product
     */
    @Select({
            "select id,serial_id, product_name,product_type,pro_profit,investment_cycle,min_amount,max_amount,repayment_way,status,",
            "is_shelves,product_intro,more_information,thumnail",
            "from product",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("NormalProduct")
    Product selectById(Long id);

    @Select({
            "select product_name from product where id= #{id,jdbcType=BIGINT}"
    })
    Product selectForDeal(Long id);

    @Select({
            "select product_name,pro_profit,investment_cycle,min_amount,max_amount",
            "from product where id= #{id,jdbcType=BIGINT}"
    })
    @ResultMap("NormalProduct")
    Product selectForCompact(Long id);


}