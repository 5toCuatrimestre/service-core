package jbar.service_core.Multimedia.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jbar.service_core.Product_Category.Model.ProductCategory;
import jbar.service_core.Product_Multimedia.ProductMultimedia;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="multimedia")
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer multimediaId;
    @Column(name = "url", columnDefinition = "VARCHAR(1000)",nullable = false)
    private String url;
    @JsonIgnore
    @OneToMany(mappedBy = "multimedia", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductMultimedia> multimedias;


    public Multimedia(){
    }
    public Multimedia(Integer multimediaId, String url) {
        this.multimediaId = multimediaId;
        this.url = url;
    }

    public Multimedia(String url) {
    }

    public Integer getMultimediaId() {return multimediaId;}

    public void setMultimediaId(Integer multimediaId) { this.multimediaId=multimediaId;}

    public String getUrl() {return url;}

    public void setUrl(String url) {this.url=url;}

}