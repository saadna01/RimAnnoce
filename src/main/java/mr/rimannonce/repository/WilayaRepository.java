package mr.rimannonce.repository;

import mr.rimannonce.domain.Wilaya;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Wilaya entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WilayaRepository extends JpaRepository<Wilaya, Long> {

}
