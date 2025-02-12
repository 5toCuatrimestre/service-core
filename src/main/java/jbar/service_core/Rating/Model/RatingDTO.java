package jbar.service_core.Rating.Model;

import jakarta.validation.constraints.*;

public class RatingDTO {
    @NotNull(message = "Sell ID is required")
    private Integer sellId;

    @NotNull(message = "Waiter ID is required")
    private Integer waiterId;

    @NotNull(message = "Stars rating is required")
    @Min(value = 1, message = "Minimum rating is 1 star")
    @Max(value = 5, message = "Maximum rating is 5 stars")
    private Integer stars;

    private String comment;

    public RatingDTO() {}

    public RatingDTO(Integer sellId, Integer waiterId, Integer stars, String comment) {
        this.sellId = sellId;
        this.waiterId = waiterId;
        this.stars = stars;
        this.comment = comment;
    }

    // Getters y Setters
    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public Integer getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Integer waiterId) {
        this.waiterId = waiterId;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
