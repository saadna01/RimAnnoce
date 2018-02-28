package mr.rimannonce.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Mouqataa entity.
 */
public class MouqataaDTO implements Serializable {

    private Long id;

    private String mouqataaName;

    private Long wilayaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMouqataaName() {
        return mouqataaName;
    }

    public void setMouqataaName(String mouqataaName) {
        this.mouqataaName = mouqataaName;
    }

    public Long getWilayaId() {
        return wilayaId;
    }

    public void setWilayaId(Long wilayaId) {
        this.wilayaId = wilayaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MouqataaDTO mouqataaDTO = (MouqataaDTO) o;
        if(mouqataaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mouqataaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MouqataaDTO{" +
            "id=" + getId() +
            ", mouqataaName='" + getMouqataaName() + "'" +
            "}";
    }
}
