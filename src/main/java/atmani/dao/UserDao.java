package atmani.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import atmani.model.User;

public interface UserDao extends JpaRepository<User, Integer> {

}
