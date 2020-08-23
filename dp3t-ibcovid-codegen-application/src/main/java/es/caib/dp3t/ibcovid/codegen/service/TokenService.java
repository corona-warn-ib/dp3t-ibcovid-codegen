package es.caib.dp3t.ibcovid.codegen.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    @Value("jwt.PRIVATE_KEY_FILE")
    private static final String PRIVATE_KEY_FILE = "classpath://generated_private_base64.pem";
    private static final String ALGORITHM = "EC";
    private static final String CCAA_SUBJECT = "04";
    private static final String CCAA_ISSUER = "ISSUER";
    private static final int TOKEN_MINS_EXPIRES = 10;

    @Value("${application.radarcovid.publicKey}")
    private final String keyPublic = null;


    public TokenService() {

    }

    private String run() throws Exception {

        String strPrivateKey = getBase64Key(loadKey(PRIVATE_KEY_FILE));
        ECPrivateKey privateKey = (ECPrivateKey) loadPrivateKeyFromPem(strPrivateKey, ALGORITHM);

        Algorithm algorithm = Algorithm.ECDSA512(null, privateKey);


        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS).minus(2, ChronoUnit.MINUTES);
        Instant expiresAt = issuedAt.plus(TOKEN_MINS_EXPIRES, ChronoUnit.MINUTES);

        String token = JWT.create().withJWTId(UUID.randomUUID().toString()).withSubject(CCAA_SUBJECT).withIssuer(CCAA_ISSUER)
                .withIssuedAt(Date.from(issuedAt))
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
        System.out.println("JWT = " + token);
        return token;
    }

    public String createToken() {
        try {
            return this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadKey(String resource) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = this.getClass().getResourceAsStream(resource.substring(11));
        new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private String getBase64Key(String key) {
        return new String(Base64.getDecoder().decode(key));
    }

    private PrivateKey loadPrivateKeyFromPem(String privatePart, String algorithm) {
        PemReader readerPem = null;
        PrivateKey result = null;
        try {
            var reader = new StringReader(privatePart);
            readerPem = new PemReader(reader);
            var obj = readerPem.readPemObject();
            result = KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(obj.getContent()));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            System.err.println("Exception loading private key from PEM: " + e.getMessage());
        } finally {
            try {
                readerPem.close();
            } catch (IOException e) {
                System.err.println("Exception closing PEM reader: " + e.getMessage());
            }
        }
        return result;
    }

}