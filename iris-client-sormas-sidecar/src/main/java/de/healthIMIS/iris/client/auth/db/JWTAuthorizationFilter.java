package de.healthIMIS.iris.client.auth.db;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.healthIMIS.iris.client.auth.db.jwt.JWTVerifier;
import de.healthIMIS.iris.client.users.web.UserController;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static de.healthIMIS.iris.client.auth.db.JWTAuthenticationFilter.USER_ROLE_CLAIM;
import static de.healthIMIS.iris.client.auth.db.SecurityConstants.BEARER_TOKEN_PREFIX;
import static de.healthIMIS.iris.client.users.web.UserController.USER_API_PATH;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTVerifier jwtVerifier) {
        super(authManager);
        this.jwtVerifier = jwtVerifier;
    }

    public JWTVerifier jwtVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(BEARER_TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        var token = header.replace(BEARER_TOKEN_PREFIX, "");

        var authentication = authenticate(token);

        // only admins are allowed to access the user api
        if (req.getRequestURI().startsWith(USER_API_PATH)) {
            if (!authentication.isAdmin()) {
                chain.doFilter(req, res);
                return;
            }
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    /**
     * This method is called if the user supplied a jwt token.
     * @param token JSON Web Token
     * @return
     */
    private UserAccountAuthentication authenticate(String token) {
        DecodedJWT jwt = jwtVerifier.verify(token);

        var userName = jwt.getSubject();

        if (userName != null) {
            var authority = new SimpleGrantedAuthority(jwt.getClaim(USER_ROLE_CLAIM).asString());
            return new UserAccountAuthentication(userName, true, List.of(authority));
        }
        return null;
    }

}
