package es.caib.dp3t.ibcovid.codegen.controller;

import es.caib.dp3t.ibcovid.codegen.common.utils.DateUtils;
import es.caib.dp3t.ibcovid.codegen.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.codegen.controller.mapper.ExposedAccessCodeDtoMapper;
import es.caib.dp3t.ibcovid.codegen.controller.model.CreateExposedAccessCodeRQDto;
import es.caib.dp3t.ibcovid.codegen.controller.model.CreateExposedAccessCodeRSDto;
import es.caib.dp3t.ibcovid.codegen.service.CodeGenService;
import es.caib.dp3t.ibcovid.codegen.service.model.CreateExposedAccessCodeRQSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.ExposedAccessCodeSrvDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = RouteConstants.CODE_GEN_ADMIN_BASE_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Code generation administration operations")
public class CodeGenAdminController {

    private final CodeGenService codeGenAdminService;

    public CodeGenAdminController(final CodeGenService codeGenAdminService) {
        this.codeGenAdminService = codeGenAdminService;
    }

    /**
     *
     * @deprecated It was deprecated because app doen't proced codes in new versions.
     *
     */
    @Deprecated
    @PostMapping(value = "")
    @ApiOperation(value = "Generates an access code to allow uploading the exposed keys keys")
    public ResponseEntity<CreateExposedAccessCodeRSDto> createExposedAccessCode(
            @Valid @RequestBody final CreateExposedAccessCodeRQDto request) {
        log.debug("BEGIN - createExposedAccessCode: params={}", request);

        if (request.getOnsetDate() != null && !DateUtils.isUTCMidnight(request.getOnsetDate())) {
            ResponseEntity.badRequest();
        }

        final CreateExposedAccessCodeRQSrvDto srvRequest = ExposedAccessCodeDtoMapper.INSTANCE.requestToSrvRequest(request);
        final ExposedAccessCodeSrvDto srvResponse = codeGenAdminService.createExposedAccessCode(srvRequest);
        final CreateExposedAccessCodeRSDto response = ExposedAccessCodeDtoMapper.INSTANCE.srvDtoToDto(srvResponse);

        log.debug("END - createExposedAccessCode: response={}", response);
        return ResponseEntity.ok(response);
    }

}
