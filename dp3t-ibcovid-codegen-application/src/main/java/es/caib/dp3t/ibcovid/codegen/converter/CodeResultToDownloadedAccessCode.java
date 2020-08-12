package es.caib.dp3t.ibcovid.codegen.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import es.caib.dp3t.ibcovid.codegen.controller.client.codes.model.CodesResult;
import es.caib.dp3t.ibcovid.codegen.data.model.DownloadedAccessCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CodeResultToDownloadedAccessCode implements Converter<CodesResult, DownloadedAccessCode> {

    @Override
    public DownloadedAccessCode convert(final CodesResult codesResult) {

        return DownloadedAccessCode.builder()
                .accessCode(codesResult.getCodes().get(0))
                .build();
    }
}
