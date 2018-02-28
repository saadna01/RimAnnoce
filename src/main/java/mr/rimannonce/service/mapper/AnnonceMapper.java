package mr.rimannonce.service.mapper;

import mr.rimannonce.domain.*;
import mr.rimannonce.service.dto.AnnonceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Annonce and its DTO AnnonceDTO.
 */
@Mapper(componentModel = "spring", uses = {MouqataaMapper.class, CategorieMapper.class, UserMapper.class})
public interface AnnonceMapper extends EntityMapper<AnnonceDTO, Annonce> {

    @Mapping(source = "mouqataa.id", target = "mouqataaId")
    @Mapping(source = "categorie.id", target = "categorieId")
    @Mapping(source = "user.id", target = "userId")
    AnnonceDTO toDto(Annonce annonce);

    @Mapping(source = "mouqataaId", target = "mouqataa")
    @Mapping(source = "categorieId", target = "categorie")
    @Mapping(source = "userId", target = "user")
    Annonce toEntity(AnnonceDTO annonceDTO);

    default Annonce fromId(Long id) {
        if (id == null) {
            return null;
        }
        Annonce annonce = new Annonce();
        annonce.setId(id);
        return annonce;
    }
}
