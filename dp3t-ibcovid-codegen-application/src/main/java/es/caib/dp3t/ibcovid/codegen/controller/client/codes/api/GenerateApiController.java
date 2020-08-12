package es.caib.dp3t.ibcovid.codegen.controller.client.codes.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-10T16:59:47.385+02:00[Europe/Paris]")
@Controller
@RequestMapping("${openapi.esGobCovid19RadarcovidVerification.base-path:/verification}")
public class GenerateApiController implements GenerateApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public GenerateApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
