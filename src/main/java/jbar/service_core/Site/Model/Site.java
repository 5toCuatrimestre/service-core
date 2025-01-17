package jbar.service_core.Site.Model;
import jakarta.persistence.*;

@Entity
@Table(name="site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer siteId;
}
