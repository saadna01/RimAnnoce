package mr.rimannonce.repository;

import mr.rimannonce.domain.Mouqataa;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Mouqataa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouqataaRepository extends JpaRepository<Mouqataa, Long> {

}
