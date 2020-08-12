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
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;

@Service
public class TokenService {

    private static final String PRIVATE_KEY_FILE = "classpath://generated_private_base64.pem";
    private static final String ALGORITHM = "EC";
    private static final String CCAA_SUBJECT = "00";
    private static final String CCAA_ISSUER = "ISSUER";
    private static final int TOKEN_MINS_EXPIRES = 15;

    @Value("${application.radarcovid.publicKey}")
    private final String keyPublic = null;


    public TokenService() {

    }

    private String run() throws Exception {

//        // Load BouncyCastle as JCA provider
//        Security.addProvider(new BouncyCastleProvider());
//
//        // Parse the EC key pair
//        PEMParser pemParser = new PEMParser(new InputStreamReader(new FileInputStream("ec512-key-pair.pem")));
//        PEMKeyPair pemKeyPair = (PEMKeyPair)pemParser.readObject();

        String strPrivateKey = getBase64Key(loadKey(PRIVATE_KEY_FILE));
        ECPrivateKey privateKey = (ECPrivateKey) loadPrivateKeyFromPem(strPrivateKey, ALGORITHM);

        Algorithm algorithm = Algorithm.ECDSA512(null, privateKey);

        String token = JWT.create().withSubject(CCAA_SUBJECT).withIssuer(CCAA_ISSUER).withIssuedAt(Date.from(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).toInstant())).withExpiresAt(Date.from(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC).plusMinutes(TOKEN_MINS_EXPIRES).toInstant())).sign(algorithm);
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