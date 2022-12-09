package atmani.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WsConfigurerAdapter {
	
	@Autowired
	CustomerUsersDetailsService customerUsersDetailsService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(customerUsersDetailsService);
	}
}
