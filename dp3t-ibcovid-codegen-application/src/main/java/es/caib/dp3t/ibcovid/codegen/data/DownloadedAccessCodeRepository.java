package es.caib.dp3t.ibcovid.codegen.data;

import es.caib.dp3t.ibcovid.codegen.data.model.DownloadedAccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DownloadedAccessCodeRepository extends JpaRepository<DownloadedAccessCode, Long> {

    //Optional<DownloadedAccessCode> findByIdWithLock(Long id);

    /*@Modifying
    @Query("update DownloadedAccessCode set activatedAt = current_date where max(activatedAt)")
    public void getDownloadedAccessCode();*/

}
