package es.caib.dp3t.ibcovid.codegen.common.config.security.filter;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final static String BEARER = "Bearer";
    private String tokenSign;

    public JWTAuthorizationFilter(String tokenSign){
        this.tokenSign = tokenSign;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(this.existsJWTToken(httpServletRequest, httpServletResponse)){
                final boolean isSigned = this.validateToken(httpServletRequest);
                if (isSigned) {
                    setUpSpringAuthentication();
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    /**
     * Metodo para autenticarnos dentro del flujo de Spring
     *
     */
    private void setUpSpringAuthentication() {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("AUTHENTICATED", null,
                null);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean existsJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationHeader == null || !authenticationHeader.startsWith(BEARER))
            return false;
        return true;
    }

    private boolean validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace(BEARER, StringUtils.EMPTY);
        return Jwts.parser().setSigningKey(tokenSign.getBytes()).isSigned(jwtToken);
    }
}
