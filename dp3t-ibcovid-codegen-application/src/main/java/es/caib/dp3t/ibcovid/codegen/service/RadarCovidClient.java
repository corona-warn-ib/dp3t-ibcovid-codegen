package es.caib.dp3t.ibcovid.codegen.service;

import es.caib.dp3t.ibcovid.codegen.common.exception.SediaInvalidSignatureException;
import es.caib.dp3t.ibcovid.codegen.controller.client.codes.api.GenerateApi;
import es.caib.dp3t.ibcovid.codegen.controller.client.codes.model.CodesResult;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;

@RestController
@Validated
public class RadarCovidClient implements GenerateApi {

    private static final String ALGORITHM = "SHA512withECDSA";

    private static final Logger log = LoggerFactory.getLogger(RadarCovidClient.class);


    @Value("${application.radarcovid.url}")
    private String url;

    @Value("${application.radarcovid.publicKey}")
    private String radarCovidPublicKey;

    private static final String AUTH_HEADER = "x-sedia-authorization";

    private final TokenService tokenService;
    private final ConversionService conversionService;

    @Autowired
    RadarCovidClient(final TokenService tokenService,
                     final ConversionService conversionService){
        this.tokenService = tokenService;
        this.conversionService = conversionService;
    }

    @Override
    @GetMapping("/getCodes")
    public ResponseEntity<CodesResult> getCodes(@RequestParam("n") @NotNull @Valid Integer n
        , final boolean testToken) throws SediaInvalidSignatureException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String token = tokenService.createToken();
        if(testToken){
            token = StringUtils.EMPTY;
        }
        httpHeaders.set(AUTH_HEADER, token);

        HttpEntity request = new HttpEntity(httpHeaders);

        ResponseEntity<CodesResult> result = restTemplate.exchange(url, HttpMethod.GET, request, CodesResult.class, n);
        log.info("Result = {}", result);



        StringBuilder stringBuilder = new StringBuilder();
        result.getBody().getCodes().stream().forEach(stringBuilder::append);

        boolean validated = false;
        try {
            String strPublicKey = getBase64Key(radarCovidPublicKey);
            ECPublicKey publicKey = (ECPublicKey) loadPublicKeyFromPem(strPublicKey, "EC");
            Signature signature = Signature.getInstance(ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(stringBuilder.toString().getBytes("UTF-8"));
            validated = signature.verify(Base64.getDecoder().decode(result.getBody().getSignature().getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("Excepción validando firma: {}", e.getMessage(), e);
            throw new SediaInvalidSignatureException("Invalid sedia signature.");
        } log.info("Signature validated: {}", validated);

        return result;
    }

    private String getBase64Key(String key) {
        return new String(Base64.getDecoder().decode(key));
    }

    private PublicKey loadPublicKeyFromPem(String publicPart, String algorithm) {
        PemReader readerPem = null;
        PublicKey result = null;
        try {
            var reader = new StringReader(publicPart);
            readerPem = new PemReader(reader);
            var obj = readerPem.readPemObject();
            readerPem.close();
            result = KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(obj.getContent()));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            log.error("Exception loading public key from PEM", e);
        } finally {
            if (readerPem != null) {
                try {
                    readerPem.close();
                } catch (IOException e) {
                    log.error("Exception closing PEM reader: {}", e.getMessage(), e);
                }
            }
        }
        return result;
    }

}