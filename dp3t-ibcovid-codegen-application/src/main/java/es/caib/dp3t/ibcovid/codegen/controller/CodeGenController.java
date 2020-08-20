package es.caib.dp3t.ibcovid.codegen.controller;

import es.caib.dp3t.ibcovid.codegen.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.codegen.controller.mapper.AuthenticationTokenDtoMapper;
import es.caib.dp3t.ibcovid.codegen.controller.model.GetAuthenticationTokenRQDto;
import es.caib.dp3t.ibcovid.codegen.controller.model.GetAuthenticationTokenRSDto;
import es.caib.dp3t.ibcovid.codegen.service.CodeGenService;
import es.caib.dp3t.ibcovid.codegen.service.model.AuthenticationTokenSrvDto;
import es.caib.dp3t.ibcovid.codegen.service.model.GetAuthenticationTokenRQSrvDto;
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
@RequestMapping(value = RouteConstants.CODE_GEN_BASE_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Code generation operations")
public class CodeGenController {

    private final CodeGenService codeGenService;

    public CodeGenController(final CodeGenService codeGenService) {
        this.codeGenService = codeGenService;
    }

    /**
     *
     * @deprecated It was deprecated because app doen't proced codes in new versions.
     *
     */
    @Deprecated
    @PostMapping(value = "/onset")
    @ApiOperation(value = "Gets the access token for allowing to upload the exposed keys")
    public ResponseEntity<GetAuthenticationTokenRSDto> getAuthenticationToken(
            @Valid @RequestBody final GetAuthenticationTokenRQDto request) {
        log.debug("BEGIN - getAuthenticationToken: params={}", request);

        final GetAuthenticationTokenRQSrvDto srvRequest = AuthenticationTokenDtoMapper.INSTANCE.requestToSrvRequest(request);
        final AuthenticationTokenSrvDto srvResponse = codeGenService.getAuthenticationToken(srvRequest);
        final GetAuthenticationTokenRSDto response = AuthenticationTokenDtoMapper.INSTANCE.srvDtoToDto(srvResponse);

        log.debug("END - getAuthenticationToken: response={}", response);
        return ResponseEntity.ok(response);
    }

}
