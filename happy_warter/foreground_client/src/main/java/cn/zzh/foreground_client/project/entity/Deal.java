package cn.zzh.foreground_client.project.entity;

import java.math.BigDecimal;

public class Deal {
    private Long id;
    private String serialId;
    private Long userId;
    private Long productId;
    private BigDecimal investment;
    private BigDecimal profit;
    private Integer dealingType;
    private Long createdAt;
    private Long updatedAt;
    private Product product;

    public Deal(Long id, String serialId, Long userId, Long productId, BigDecimal investment, BigDecimal profit,  Integer dealingType, Long createdAt, Long updatedAt) {
        this.id = id;
        this.serialId = serialId;
        this.userId = userId;
        this.productId = productId;
        this.investment = investment;
        this.profit = profit;
        this.dealingType = dealingType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Deal(Long id, String serialId, Long userId, Long productId, BigDecimal investment, BigDecimal profit,  Integer dealingType, Long createdAt, Long updatedAt,Product product) {
        this.id = id;
        this.serialId = serialId;
        this.userId = userId;
        this.productId = productId;
        this.investment = investment;
        this.profit = profit;
        this.dealingType = dealingType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.product=product;
    }

    public Deal(){}

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", serialId='" + serialId + '\'' +
                ", userId=" + userId +
                ", productId=" + productId +
                ", investment=" + investment +
                ", profit=" + profit +
                ", dealingType=" + dealingType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "\n"+
                ", product=" + product +
                '}'+"\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getInvestment() {
        return investment;
    }

    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Integer getDealingType() {
        return dealingType;
    }

    public void setDealingType(Integer dealingType) {
        this.dealingType = dealingType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}