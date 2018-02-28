package mr.rimannonce.service.mapper;

import mr.rimannonce.domain.*;
import mr.rimannonce.service.dto.MouqataaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mouqataa and its DTO MouqataaDTO.
 */
@Mapper(componentModel = "spring", uses = {WilayaMapper.class})
public interface MouqataaMapper extends EntityMapper<MouqataaDTO, Mouqataa> {

    @Mapping(source = "wilaya.id", target = "wilayaId")
    MouqataaDTO toDto(Mouqataa mouqataa);

    @Mapping(source = "wilayaId", target = "wilaya")
    Mouqataa toEntity(MouqataaDTO mouqataaDTO);

    default Mouqataa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mouqataa mouqataa = new Mouqataa();
        mouqataa.setId(id);
        return mouqataa;
    }
}
