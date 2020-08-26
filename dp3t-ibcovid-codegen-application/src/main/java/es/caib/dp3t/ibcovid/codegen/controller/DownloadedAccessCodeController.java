package es.caib.dp3t.ibcovid.codegen.controller;

import es.caib.dp3t.ibcovid.codegen.common.exception.SediaInvalidSignatureException;
import es.caib.dp3t.ibcovid.codegen.controller.client.codes.model.CodesResult;
import es.caib.dp3t.ibcovid.codegen.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.codegen.service.DownloadedAccessCodeService;
import es.caib.dp3t.ibcovid.codegen.service.impl.SmsService;
import es.caib.dp3t.ibcovid.codegen.service.model.DownloadedAccessCodeSrvDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RouteConstants.DOWNLOADED_CODE_ADMIN_BASE_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Downloaded code administration operations")
public class DownloadedAccessCodeController {
    @Value("${application.radarcovid.limit}")
    private Integer codesLimit;
    private final DownloadedAccessCodeService downloadedAccessCodeService;
    private final SmsService smsService;

    public DownloadedAccessCodeController(final DownloadedAccessCodeService downloadedAccessCodeService,
                                          final SmsService smsService) {
        this.downloadedAccessCodeService = downloadedAccessCodeService;
        this.smsService = smsService;
    }

    @GetMapping(value = "")
    @ApiOperation(value = "Get one downloaded access code")
    public ResponseEntity<DownloadedAccessCodeSrvDto> getDownloadedAccessCode() {
        log.debug("BEGIN - getDownloadedAccessCode");
        DownloadedAccessCodeSrvDto code  = this.downloadedAccessCodeService.getDownloadedAccessCode();
        log.debug("END - getDownloadedAccessCode", code);
        if(code != null){
            return ResponseEntity.ok(code);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/generate-codes")
    @Scheduled(cron = "${dp3t.ibcovid.codegen.tasks.delete-exposed-access-codes.cron}")
    public void executeGenerateCodes() throws SediaInvalidSignatureException {
        log.info("Inicia el proceso de Generacion de Codigos");
        final List<DownloadedAccessCodeSrvDto> downloadedAccessCodeList = this.downloadedAccessCodeService.getDownloadedAccessCodeList();
        if(downloadedAccessCodeList.size() < this.codesLimit){
            downloadedAccessCodeService.generateCodes();
        }
        log.info("Finaliza el proceso de Generacion de Codigos");
    }

    @GetMapping("/generate-codes/test")
    public ResponseEntity<CodesResult> executeGenerateCodesTest(@RequestParam final Integer number
        , @RequestParam final boolean testToken) throws SediaInvalidSignatureException {
        return downloadedAccessCodeService.generateCodesTest(number, testToken);
    }

    @GetMapping("/send-sms")
    public void sendSms(@RequestParam("number") final String number
            , @RequestParam("code") final String code) {
        log.info("BEGIN - sendSms");
        smsService.sendSms(number, code);
        log.debug("END - sendSms");
    }



}
