package cn.zzh.foreground_client.project.entity;

public class Idea {
    private Long id;
    private String serialId;
    private Long userId;
    private Long replyBy;
    private Long replyTime;
    private Byte status;
    private Long createdAt;
    private Long updatedAt;
    private String content;


    public Idea(Long id, String serialId, Long userId, Long replyBy, Long replyTime, Byte status, Long createdAt, Long updatedAt, String content) {
        this.id = id;
        this.serialId = serialId;
        this.userId = userId;
        this.replyBy = replyBy;
        this.replyTime = replyTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
    }
    public Idea(){

    }

    @Override
    public String toString() {
        return "Idea{" +
                "id=" + id +
                ", serialId='" + serialId + '\'' +
                ", userId=" + userId +
                ", replyBy=" + replyBy +
                ", replyTime=" + replyTime +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", content='" + content + '\'' +
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

    public Long getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(Long replyBy) {
        this.replyBy = replyBy;
    }

    public Long getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Long replyTime) {
        this.replyTime = replyTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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