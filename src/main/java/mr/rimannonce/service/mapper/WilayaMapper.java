package mr.rimannonce.service.mapper;

import mr.rimannonce.domain.*;
import mr.rimannonce.service.dto.WilayaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Wilaya and its DTO WilayaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WilayaMapper extends EntityMapper<WilayaDTO, Wilaya> {



    default Wilaya fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wilaya wilaya = new Wilaya();
        wilaya.setId(id);
        return wilaya;
    }
}
