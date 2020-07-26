package es.caib.dp3t.ibcovid.codegen.data;

import es.caib.dp3t.ibcovid.codegen.data.model.ExposedAccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ExposedAccessCodeRepository extends JpaRepository<ExposedAccessCode, Long> {

    Optional<ExposedAccessCode> findByAccessCode(final String code);

    Optional<ExposedAccessCode> findByAccessCodeAndExpireAtGreaterThanAndActiveIsTrue(
            final String code, final LocalDateTime expirationTime);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM ExposedAccessCode exp WHERE exp.expireAt <= :currentTime ")
    int deleteExpiredAccessCodes(@Param("currentTime") final LocalDateTime currentTime);

}
