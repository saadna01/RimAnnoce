package mr.rimannonce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import mr.rimannonce.domain.enumeration.AnnonceStatus;

/**
 * A Annonce.
 */
@Entity
@Table(name = "annonce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Annonce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "details")
    private String details;

    @Lob
    @Column(name = "photo_1")
    private byte[] photo1;

    @Column(name = "photo_1_content_type")
    private String photo1ContentType;

    @Lob
    @Column(name = "photo_2")
    private byte[] photo2;

    @Column(name = "photo_2_content_type")
    private String photo2ContentType;

    @Lob
    @Column(name = "photo_3")
    private byte[] photo3;

    @Column(name = "photo_3_content_type")
    private String photo3ContentType;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "jhi_date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AnnonceStatus status;

    @ManyToOne
    private Mouqataa mouqataa;

    @ManyToOne
    private Categorie categorie;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Annonce title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public Annonce details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public byte[] getPhoto1() {
        return photo1;
    }

    public Annonce photo1(byte[] photo1) {
        this.photo1 = photo1;
        return this;
    }

    public void setPhoto1(byte[] photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto1ContentType() {
        return photo1ContentType;
    }

    public Annonce photo1ContentType(String photo1ContentType) {
        this.photo1ContentType = photo1ContentType;
        return this;
    }

    public void setPhoto1ContentType(String photo1ContentType) {
        this.photo1ContentType = photo1ContentType;
    }

    public byte[] getPhoto2() {
        return photo2;
    }

    public Annonce photo2(byte[] photo2) {
        this.photo2 = photo2;
        return this;
    }

    public void setPhoto2(byte[] photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto2ContentType() {
        return photo2ContentType;
    }

    public Annonce photo2ContentType(String photo2ContentType) {
        this.photo2ContentType = photo2ContentType;
        return this;
    }

    public void setPhoto2ContentType(String photo2ContentType) {
        this.photo2ContentType = photo2ContentType;
    }

    public byte[] getPhoto3() {
        return photo3;
    }

    public Annonce photo3(byte[] photo3) {
        this.photo3 = photo3;
        return this;
    }

    public void setPhoto3(byte[] photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto3ContentType() {
        return photo3ContentType;
    }

    public Annonce photo3ContentType(String photo3ContentType) {
        this.photo3ContentType = photo3ContentType;
        return this;
    }

    public void setPhoto3ContentType(String photo3ContentType) {
        this.photo3ContentType = photo3ContentType;
    }

    public Double getPrix() {
        return prix;
    }

    public Annonce prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Instant getDate() {
        return date;
    }

    public Annonce date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public AnnonceStatus getStatus() {
        return status;
    }

    public Annonce status(AnnonceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AnnonceStatus status) {
        this.status = status;
    }

    public Mouqataa getMouqataa() {
        return mouqataa;
    }

    public Annonce mouqataa(Mouqataa mouqataa) {
        this.mouqataa = mouqataa;
        return this;
    }

    public void setMouqataa(Mouqataa mouqataa) {
        this.mouqataa = mouqataa;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Annonce categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public User getUser() {
        return user;
    }

    public Annonce user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Annonce annonce = (Annonce) o;
        if (annonce.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annonce.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Annonce{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            ", photo1='" + getPhoto1() + "'" +
            ", photo1ContentType='" + getPhoto1ContentType() + "'" +
            ", photo2='" + getPhoto2() + "'" +
            ", photo2ContentType='" + getPhoto2ContentType() + "'" +
            ", photo3='" + getPhoto3() + "'" +
            ", photo3ContentType='" + getPhoto3ContentType() + "'" +
            ", prix=" + getPrix() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
