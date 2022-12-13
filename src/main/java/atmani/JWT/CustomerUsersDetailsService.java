package atmani.JWT;

import java.util.ArrayList;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import atmani.dao.UserDao;
import atmani.model.User;
import atmani.servicesIMP.UserServiceIMP;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService { 
	
	@Autowired
	UserDao userDao;
	
	private User userDetail;
	
	Logger log = (Logger) LoggerFactory.getLogger(UserServiceIMP.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//
		log.info("Inside loadUserByUsername {} ", username);
		System.out.printf("Inside loadUserByUsername {} ", username);
		// TODO Auto-generated method stub
		userDetail = userDao.findByEmail(username);
		if (!Objects.isNull(userDetail)) {
			return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
			
		}else
			throw new UsernameNotFoundException("User Not Found!");
	}
	
	public User getUserDetail() {
		return userDetail;
	}

}