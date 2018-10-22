package cn.zzh.foreground_client.project.entity;

import java.math.BigDecimal;

public class Compact {
    private Long id;
    private String serialId;
    private Long userId;
    private Long productId;
    private Long expireTime;
    private Byte status;
    private Byte isMatch;
    private BigDecimal amount;
    private BigDecimal profit;
    private Integer financialTime;
    private Long repaymentWay;
    private Long createdAt;
    private Long updatedAt;
    private Product product;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Compact(Long id, String serialId, Long userId, Long productId, Long expireTime, Byte status, Byte isMatch, BigDecimal amount, BigDecimal profit, Integer financialTime, Long repaymentWay, Long createdAt, Long updatedAt, Product product,User user) {
        this.id = id;
        this.serialId = serialId;
        this.userId = userId;
        this.productId = productId;
        this.expireTime = expireTime;
        this.status = status;
        this.isMatch = isMatch;
        this.amount = amount;
        this.profit = profit;
        this.financialTime = financialTime;
        this.repaymentWay = repaymentWay;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.product = product;
        this.user=user;
    }

    public Compact(Long id, String serialId, Long userId, Long productId, Long expireTime, Byte status, Byte isMatch, BigDecimal amount, BigDecimal profit, Integer financialTime, Long repaymentWay, Long createdAt, Long updatedAt) {
        this.id = id;
        this.serialId = serialId;
        this.userId = userId;
        this.productId = productId;
        this.expireTime = expireTime;
        this.status = status;
        this.isMatch = isMatch;
        this.amount = amount;
        this.profit = profit;
        this.financialTime = financialTime;
        this.repaymentWay = repaymentWay;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Compact(){

    }

    @Override
    public String toString() {
        return "Compact{" +
                "id=" + id +
                ", serialId='" + serialId + '\'' +
                ", userId=" + userId +
                ", productId=" + productId +
                ", expireTime=" + expireTime +
                ", status=" + status +
                ", isMatch=" + isMatch +
                ", amount=" + amount +
                ", profit=" + profit +
                ", financialTime=" + financialTime +
                ", repaymentWay=" + repaymentWay +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "\n"+
                ", product=" + product +
                "\n"+
                ", user=" + user +
                '}';
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

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(Byte isMatch) {
        this.isMatch = isMatch;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Integer getFinancialTime() {
        return financialTime;
    }

    public void setFinancialTime(Integer financialTime) {
        this.financialTime = financialTime;
    }

    public Long getRepaymentWay() {
        return repaymentWay;
    }

    public void setRepaymentWay(Long repaymentWay) {
        this.repaymentWay = repaymentWay;
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