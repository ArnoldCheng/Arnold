package cn.zzh.foreground_client.project.entity;

import javax.validation.constraints.Null;

public class Message {
    private Long id;
    private Long userId;
    private Long managerId;
    private String title;
    private String type;
    private Integer isRead;
    private Long sendAt;
    @Null
    private String createdBy;
    @Null
    private String updatedBy;
    private Long createdAt;
    private Long updatedAt;
    private String content;
    public Message(Long id, Long userId, Long managerId, String title, String type, Integer isRead, Long sendAt, String createdBy, String updatedBy, Long createdAt, Long updatedAt, String content) {
        this.id = id;
        this.userId = userId;
        this.managerId = managerId;
        this.title = title;
        this.type = type;
        this.isRead = isRead;
        this.sendAt = sendAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
    }

    public Message(){

    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", managerId=" + managerId +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", isRead=" + isRead +
                ", sendAt=" + sendAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "\n"+
                ", content='" + content + '\'' +
                '}'+"\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Long getSendAt() {
        return sendAt;
    }

    public void setSendAt(Long sendAt) {
        this.sendAt = sendAt;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}