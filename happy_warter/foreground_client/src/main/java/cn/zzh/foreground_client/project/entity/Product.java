package cn.zzh.foreground_client.project.entity;

import java.math.BigDecimal;

public class Product {

    private Long id;
    private String serialId;
    private String productName;
    private Integer productType;
    private BigDecimal proProfit;
    private Integer investmentCycle;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String repaymentWay;
    private Integer status;
    private Integer isShelves;
    private String moreInformation;
    private String thumnail;
    private String createdBy;
    private String updatedBy;
    private Long createdAt;
    private Long updatedAt;
    private String productIntro;

    public Product(){
    }

    public Product(Long id, String serialId, String productName, Integer productType, BigDecimal proProfit, Integer investmentCycle, BigDecimal minAmount, BigDecimal maxAmount, String repaymentWay, Integer status, Integer isShelves, String moreInformation, String thumnail, String createdBy, String updatedBy, Long createdAt, Long updatedAt, String productIntro) {
        this.id = id;
        this.serialId = serialId;
        this.productName = productName;
        this.productType = productType;
        this.proProfit = proProfit;
        this.investmentCycle = investmentCycle;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.repaymentWay = repaymentWay;
        this.status = status;
        this.isShelves = isShelves;
        this.moreInformation = moreInformation;
        this.thumnail = thumnail;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productIntro = productIntro;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", serialId='" + serialId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType=" + productType +
                ", proProfit=" + proProfit +
                ", investmentCycle=" + investmentCycle +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", repaymentWay='" + repaymentWay + '\'' +
                ", status=" + status +
                ", isShelves=" + isShelves +
                ", moreInformation='" + moreInformation + '\'' +
                ", thumnail='" + thumnail + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", productIntro='" + productIntro + '\'' +
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public BigDecimal getProProfit() {
        return proProfit;
    }

    public void setProProfit(BigDecimal proProfit) {
        this.proProfit = proProfit;
    }

    public Integer getInvestmentCycle() {
        return investmentCycle;
    }

    public void setInvestmentCycle(Integer investmentCycle) {
        this.investmentCycle = investmentCycle;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getRepaymentWay() {
        return repaymentWay;
    }

    public void setRepaymentWay(String repaymentWay) {
        this.repaymentWay = repaymentWay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsShelves() {
        return isShelves;
    }

    public void setIsShelves(Integer isShelves) {
        this.isShelves = isShelves;
    }

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
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

    public String getProductInro() {
        return productIntro;
    }

    public void setProductInro(String productInro) {
        this.productIntro = productInro;
    }
}