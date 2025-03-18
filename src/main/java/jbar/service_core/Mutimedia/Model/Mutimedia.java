package jbar.service_core.Mutimedia.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name="mutimedia")
public class Mutimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mutimediaId;
    @Column(name = "url", columnDefinition = "VARCHAR(1000)",nullable = false)
    private String url;


    public Mutimedia(){
    }
    public Mutimedia(Integer mutimediaId, String url) {
        this.mutimediaId = mutimediaId;
        this.url = url;
    }

    public Integer getMutimediaId() {return mutimediaId;}

    public void setMutimediaId(Integer mutimediaId) { this.mutimediaId=mutimediaId;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url=url;}

}