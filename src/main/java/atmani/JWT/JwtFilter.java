package atmani.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtUtil jwtUtil;

	Claims claims ;
	private String username;
	
	String claims2;

	@Autowired
	CustomerUsersDetailsService service;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		System.out.println("inside filter");

		if (httpServletRequest.getServletPath().matches("/user/login|/user/forgetPassword|/user/signup|/user/get")) {
			System.out.println("dofilter1");
			String authorizationHeader = httpServletRequest.getHeader("Authorization");
			String token = authorizationHeader.substring(7);
			claims = jwtUtil.extractAllClaims(token);
			System.out.println(claims+" filter1");
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		} else {
			String authorizationHeader = httpServletRequest.getHeader("Authorization");
			String token = null;

			if (authorizationHeader != null && authorizationHeader.startsWith("Bearar")) {
				token = authorizationHeader.substring(7);
				username = jwtUtil.extractUsername(token); // extractUsername
				claims = jwtUtil.extractAllClaims(token);
				System.out.println(claims+"claims");

			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = service.loadUserByUsername(username);
				if (jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				}

			}
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			
		}

	}
	

	public boolean isAdmin() {
		System.out.println("admin OK");
		System.out.println(claims+" claims");
		return "admin".equalsIgnoreCase((String) claims.get("role"));
		
	}

	public boolean isUser() {
		return "user".equalsIgnoreCase((String) claims.get("role"));
	}

	public String getCurrentUser() {
		return username;
	}

}