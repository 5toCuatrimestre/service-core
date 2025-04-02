package jbar.service_core.RatingUserSell.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.sql.Date;

@Schema(description = "Rating User Sell DTO")
public class RatingUserSellDTO {

    public interface Create {}
    public interface Update {}

    @Schema(description = "ID of the sell.", example = "1")
    @NotNull(groups = {Create.class}, message = "Sell ID is required.")
    private Integer sellId;

    @Schema(description = "ID of the user (previously waiter).", example = "5")
    @NotNull(groups = {Create.class}, message = "User ID is required.")
    private Integer userId;

    @Schema(description = "Stars rating between 1 and 5.", example = "4")
    @NotNull(groups = {Create.class}, message = "Stars rating is required.")
    @Min(value = 1, message = "Minimum rating is 1 star.")
    @Max(value = 5, message = "Maximum rating is 5 stars.")
    private Integer stars;

    @Schema(description = "Optional comment about the rating.", example = "Great service!")
    private String comment;

    @Schema(description = "Timestamp when the rating was created.", example = "2024-02-11")
    @NotNull(groups = {Create.class})
    private Date createdAt;  // Cambiar a Date

    @Schema(description = "Timestamp when the rating was last updated.", example = "2024-02-11")
    @NotNull(groups = {Update.class})
    private Date updatedAt;  // Cambiar a Date

    @Schema(description = "Timestamp when the rating was deleted (if applicable).", example = "2024-02-11")
    private Date deletedAt;  // Cambiar a Date

    public RatingUserSellDTO() {}

    public RatingUserSellDTO(Integer sellId, Integer userId, Integer stars, String comment, Date createdAt, Date updatedAt, Date deletedAt) {
        this.sellId = sellId;
        this.userId = userId;
        this.stars = stars;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
