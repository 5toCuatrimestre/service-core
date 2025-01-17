package jbar.service_core.Position.Model;
import jakarta.persistence.*;

@Entity
@Table(name="position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;
}
