package jbar.service_core.Style.Model;

import jakarta.persistence.*;

@Entity
@Table(name="style")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer styleId;
}
