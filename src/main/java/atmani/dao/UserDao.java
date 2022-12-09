package atmani.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import atmani.model.User;

public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByEmail(@Param("email") String email);

}
