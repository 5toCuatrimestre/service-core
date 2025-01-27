package jbar.service_core.User_Route.Model;

import jakarta.persistence.*;
import jbar.service_core.User.Model.User;
import jbar.service_core.Route.Model.Route;

@Entity
@Table(name = "user_route")
public class UserRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRouteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public UserRoute() {
    }

    public UserRoute(Integer userRouteId, User user, Route route, String notes) {
        this.userRouteId = userRouteId;
        this.user = user;
        this.route = route;
        this.notes = notes;
    }

    public Integer getUserRouteId() {
        return userRouteId;
    }

    public void setUserRouteId(Integer userRouteId) {
        this.userRouteId = userRouteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
