package mr.rimannonce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Mouqataa.
 */
@Entity
@Table(name = "mouqataa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mouqataa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mouqataa_name")
    private String mouqataaName;

    @ManyToOne
    private Wilaya wilaya;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMouqataaName() {
        return mouqataaName;
    }

    public Mouqataa mouqataaName(String mouqataaName) {
        this.mouqataaName = mouqataaName;
        return this;
    }

    public void setMouqataaName(String mouqataaName) {
        this.mouqataaName = mouqataaName;
    }

    public Wilaya getWilaya() {
        return wilaya;
    }

    public Mouqataa wilaya(Wilaya wilaya) {
        this.wilaya = wilaya;
        return this;
    }

    public void setWilaya(Wilaya wilaya) {
        this.wilaya = wilaya;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mouqataa mouqataa = (Mouqataa) o;
        if (mouqataa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mouqataa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mouqataa{" +
            "id=" + getId() +
            ", mouqataaName='" + getMouqataaName() + "'" +
            "}";
    }
}
