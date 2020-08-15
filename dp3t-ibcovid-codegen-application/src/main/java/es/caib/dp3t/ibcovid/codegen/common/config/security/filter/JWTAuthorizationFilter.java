package es.caib.dp3t.ibcovid.codegen.common.config.security.filter;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final static String BEARER = "Bearer";
    private String tokenSign;

    public JWTAuthorizationFilter(String tokenSign){
        this.tokenSign = tokenSign;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        boolean authenticated;
        if(this.existsJWTToken(httpServletRequest, httpServletResponse)
            && this.validateToken(httpServletRequest)){
            authenticated = Boolean.TRUE;
        } else{
            authenticated = Boolean.FALSE;
        }
        if(!authenticated){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
    }

    private boolean existsJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader == null || !authenticationHeader.startsWith(BEARER))
            return false;
        return true;
    }

    private boolean validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace(BEARER, "");
        return Jwts.parser().setSigningKey(this.tokenSign.getBytes()).isSigned(jwtToken);
    }
}
