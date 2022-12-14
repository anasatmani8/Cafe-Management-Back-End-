package atmani.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import atmani.model.User;
import atmani.wrapper.UserWrapper;

@Repository

@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByEmail(@Param("email") String email);
	
	
	
	@Query(value = "SELECT * FROM cafesystem.user where role=\"user\";", nativeQuery = true)
	List<UserWrapper> getAllUser();

}
