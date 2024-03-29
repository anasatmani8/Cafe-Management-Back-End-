package atmani.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import atmani.model.User;

@Repository

@Transactional
public interface UserDao extends JpaRepository<User, Integer> {

	User findByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM cafesystem.user where role=\"user\";", nativeQuery = true)
	List<User> getAllUser();
	
	@Query(value = "SELECT email FROM cafesystem.user where role=\"admin\";", nativeQuery = true)
	List<String> getAllAdmin();
	
	@Modifying
	@Query(value = "update User u set u.status=:status where u.id=:id")
	Integer updateStatus(@Param("status") String status, @Param("id") int id);
	
	

}
