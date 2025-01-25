package jbar.service_core.Route_Position_Site_User.Model;

import jakarta.persistence.*;
import jbar.service_core.Route.Model.Route;
import jbar.service_core.Position.Model.Position;
import jbar.service_core.Site.Model.Site;
import jbar.service_core.User.Model.User;

@Entity
@Table(name = "route_position_site_user")
public class RoutePositionSiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public RoutePositionSiteUser() {
    }

    public RoutePositionSiteUser(Integer id, Route route, Position position, Site site, User user) {
        this.id = id;
        this.route = route;
        this.position = position;
        this.site = site;
        this.user = user;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
