package it.sander.aml.application.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthorizationFilter extends GenericFilterBean {
	
	private final ObjectMapper mapper = new ObjectMapper();
		
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
    	
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String authHeader = request.getHeader(JwtConfiguration.headerParam);

        if (authHeader == null || !authHeader.startsWith(JwtConfiguration.prefix)) {
        	filterChain.doFilter(request, servletResponse);
            return;
        }
 
        String token = authHeader.substring(JwtConfiguration.prefix.length()+1);
        Authentication authentication = null;
		try {
			authentication = getAuthentication(token);
		} catch (Exception e) {
	        request.setAttribute(token, authentication);
			e.printStackTrace();
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, servletResponse);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
	private UsernamePasswordAuthenticationToken getAuthentication(String token) throws Exception {

		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtConfiguration.secret)).build().verify(token);
		String username = decodedJWT.getSubject();
		
		Claim rolesClaim = decodedJWT.getClaim("roles");
			
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(String r : rolesClaim.asList(String.class)) {
			authorities.add(new SimpleGrantedAuthority(r));
		}
		
			
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}

}
