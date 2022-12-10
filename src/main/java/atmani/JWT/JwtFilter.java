package atmani.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtil jwtUtil;
	
	Claims claims = null;
	private String username;
	
	@Autowired
	CustomerUsersDetailsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (httpServletRequest.getServletPath().matches("/user/login|/user/forgetPassword|/user/signup")) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}else {
			String authorizationHeader = httpServletRequest.getHeader("Authorization");
			String token = null;
			
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearar")) {
				token = authorizationHeader.substring(7);
				username = jwtUtil.extractUsername(token); //extractUsername
				
			}
		}
		
	}

}
