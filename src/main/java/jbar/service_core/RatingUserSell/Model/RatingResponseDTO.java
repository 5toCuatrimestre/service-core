package jbar.service_core.RatingUserSell.Model;

public class RatingResponseDTO {
    private Integer ratingId;
    private Integer userId;
    private String userName;
    private Integer sellId;
    private Integer stars;

    public RatingResponseDTO() {
    }

    public RatingResponseDTO(Integer ratingId, Integer userId, String userName, Integer sellId, Integer stars) {
        this.ratingId = ratingId;
        this.userId = userId;
        this.userName = userName;
        this.sellId = sellId;
        this.stars = stars;
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
} 