package jbar.service_core.Position_Site.Service;

import jakarta.persistence.*;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Site.Model.Site;

@Entity
@Table(name = "position_site")
public class PositionSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionSiteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    public PositionSite() {
    }

    public PositionSite(Integer positionSiteId, Position position, Site site, Integer capacity) {
        this.positionSiteId = positionSiteId;
        this.position = position;
        this.site = site;
        this.capacity = capacity;
    }

    public Integer getPositionSiteId() {
        return positionSiteId;
    }

    public void setPositionSiteId(Integer positionSiteId) {
        this.positionSiteId = positionSiteId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
