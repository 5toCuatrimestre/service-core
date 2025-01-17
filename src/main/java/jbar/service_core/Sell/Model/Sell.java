package jbar.service_core.Sell.Model;
import jakarta.persistence.*;

@Entity
@Table(name="sell")
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sellId;
}
