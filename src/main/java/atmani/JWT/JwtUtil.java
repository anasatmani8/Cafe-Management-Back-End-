
package atmani.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	private String secret = "atmaniASM";
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public Boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());
	}
	
	private String createToken(Map<String, Object> claims, String username ) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000* 60* 60* 10))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	
	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, username);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public <T> T extractClaims(String token, Function<Claims, T>ClaimsResolver) {
		final Claims claims = extractAllClaims(token);
		return ClaimsResolver.apply(claims);		
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}

}