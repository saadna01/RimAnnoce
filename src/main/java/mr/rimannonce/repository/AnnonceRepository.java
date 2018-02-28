package mr.rimannonce.repository;

import mr.rimannonce.domain.Annonce;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Annonce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    @Query("select annonce from Annonce annonce where annonce.user.login = ?#{principal.username}")
    List<Annonce> findByUserIsCurrentUser();

}
