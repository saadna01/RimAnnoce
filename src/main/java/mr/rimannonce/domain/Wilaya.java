package mr.rimannonce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Wilaya.
 */
@Entity
@Table(name = "wilaya")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wilaya implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wilaya_name")
    private String wilayaName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWilayaName() {
        return wilayaName;
    }

    public Wilaya wilayaName(String wilayaName) {
        this.wilayaName = wilayaName;
        return this;
    }

    public void setWilayaName(String wilayaName) {
        this.wilayaName = wilayaName;
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
        Wilaya wilaya = (Wilaya) o;
        if (wilaya.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wilaya.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wilaya{" +
            "id=" + getId() +
            ", wilayaName='" + getWilayaName() + "'" +
            "}";
    }
}
