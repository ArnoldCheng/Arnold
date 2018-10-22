package cn.zzh.foreground_client.project.entity;

import javax.validation.constraints.Null;

public class Banner {

    private Long id;
    private String serialId;
    private String title;
    private String thumnail;
    private Byte intervalTime;
    private Integer status;
    @Null
    private String createdBy;
    @Null
    private String updatedBy;
    @Null
    private Long createdAt;
    @Null
    private Long updatedAt;

    public Banner(Long id, String serialId, String title, String thumnail, Byte intervalTime, Integer status, String createdBy, String updatedBy, Long createdAt, Long updatedAt) {
        this.id = id;
        this.serialId = serialId;
        this.title = title;
        this.thumnail = thumnail;
        this.intervalTime = intervalTime;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", serialId='" + serialId + '\'' +
                ", title='" + title + '\'' +
                ", thumnail='" + thumnail + '\'' +
                ", intervalTime=" + intervalTime +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}'+"\n";
    }

    public Banner(){

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public Byte getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Byte intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}