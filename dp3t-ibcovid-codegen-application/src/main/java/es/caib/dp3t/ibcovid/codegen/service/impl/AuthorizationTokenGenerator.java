package es.caib.dp3t.ibcovid.codegen.service.impl;

import es.caib.dp3t.ibcovid.codegen.common.utils.DateUtils;
import es.caib.dp3t.ibcovid.codegen.data.model.ExposedAccessCode;
import es.caib.dp3t.ibcovid.codegen.service.utils.CertificateStore;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.Instant;

@Service
class AuthorizationTokenGenerator {
    private static final String ISSUER = "es.caib.dp3t.ibcovid-codegen";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.RS256;

    private final CertificateStore certificateStore;

    public AuthorizationTokenGenerator(final CertificateStore certificateStore) {
        this.certificateStore = certificateStore;
    }

    public String generateToken(final ExposedAccessCode exposedAccessCode, final int fake) {
        final PrivateKey privateKey = certificateStore.getPrivateKey();

        final Instant currentInstant = DateUtils.currentUTCInstant();
        final Instant expirationInstant = DateUtils.toInstant(exposedAccessCode.getExpireAt());

        //Let's set the JWT Claims
        return Jwts.builder()
                .setId(exposedAccessCode.getAccessCode())
                .setIssuer(ISSUER)
                .setIssuedAt(DateUtils.toDate(currentInstant))
                .setExpiration(DateUtils.toDate(expirationInstant))
                .claim("fake", fake)
                .claim("scope", "exposed")
                .claim("onset", exposedAccessCode.getOnsetDate().toString())
                .signWith(SIGNATURE_ALGORITHM, privateKey)
                .compact();
    }

}
