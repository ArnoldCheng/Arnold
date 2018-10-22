package cn.zzh.foreground_client.project.entity;

public class Bank {

    private Long id;


    private Long userId;


    private String card;


    private String type;


    private Long createdAt;


    private Long updatedAt;


    public Bank(Long id, Long userId, String card, String type, Long createdAt, Long updatedAt) {
        this.id = id;
        this.userId = userId;
        this.card = card;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public  Bank(){}

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", userId=" + userId +
                ", card='" + card + '\'' +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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