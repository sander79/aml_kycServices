package it.sander.aml.application.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Configuration
@Profile("jwt")
public class JwtConfiguration {
	
	public static String secret;
	public static String prefix;
	public static String headerParam;

	@Autowired
    public JwtConfiguration(Environment env) {
    	JwtConfiguration.secret = env.getProperty("security.secret");
    	JwtConfiguration.prefix = env.getProperty("security.prefix");
    	JwtConfiguration.headerParam = env.getProperty("security.headerParam");
        if (secret == null || prefix == null || headerParam == null) {
            throw new BeanInitializationException("Cannot assign security properties. Check application.properties file");
        }
    }
	
	protected void configure(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/api-docs/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        // this disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
 
        return http.build();
    }
    
    
    // JWTAuthorizationFilter
    public class JWTAuthorizationFilter extends GenericFilterBean {
		
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
    			e.printStackTrace();
    		}
    		
    		SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, servletResponse);
        }

        // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    	private UsernamePasswordAuthenticationToken getAuthentication(String token) throws Exception {
    		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(JwtConfiguration.secret)).build().verify(token);	
    		Claim rolesClaim = decodedJWT.getClaim("roles");
    		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    		for(String r : rolesClaim.asList(String.class)) {
    			authorities.add(new SimpleGrantedAuthority(r));
    		}		
    		return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, authorities);
    	}

    }
    
}