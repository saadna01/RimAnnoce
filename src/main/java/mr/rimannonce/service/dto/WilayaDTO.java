package mr.rimannonce.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Wilaya entity.
 */
public class WilayaDTO implements Serializable {

    private Long id;

    private String wilayaName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWilayaName() {
        return wilayaName;
    }

    public void setWilayaName(String wilayaName) {
        this.wilayaName = wilayaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WilayaDTO wilayaDTO = (WilayaDTO) o;
        if(wilayaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wilayaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WilayaDTO{" +
            "id=" + getId() +
            ", wilayaName='" + getWilayaName() + "'" +
            "}";
    }
}
