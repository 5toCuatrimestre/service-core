package jbar.service_core.Sell_Detail.Model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sell_detail_status")
public class SellDetailStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellDetailStatusId;

    @Column(name = "sell_detail_id", nullable = false)
    private Integer sellDetailId;

    @Column(name = "position_site_id", nullable = false)
    private Integer positionSiteId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_waiter", nullable = false)
    private String nameWaiter;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public SellDetailStatus() {
    }

    public SellDetailStatus(Integer sellDetailId, Integer positionSiteId, String name, String nameWaiter, Status status, Integer quantity) {
        this.sellDetailId = sellDetailId;
        this.positionSiteId = positionSiteId;
        this.name = name;
        this.nameWaiter = nameWaiter;
        this.status = status;
        this.quantity = quantity;
    }

    public Integer getSellDetailStatusId() {
        return sellDetailStatusId;
    }

    public void setSellDetailStatusId(Integer sellDetailStatusId) {
        this.sellDetailStatusId = sellDetailStatusId;
    }

    public Integer getSellDetailId() {
        return sellDetailId;
    }

    public void setSellDetailId(Integer sellDetailId) {
        this.sellDetailId = sellDetailId;
    }

    public Integer getPositionSiteId() {
        return positionSiteId;
    }

    public void setPositionSiteId(Integer positionSiteId) {
        this.positionSiteId = positionSiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWaiter() {
        return nameWaiter;
    }

    public void setNameWaiter(String nameWaiter) {
        this.nameWaiter = nameWaiter;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
} 