package atmani.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import atmani.model.Product;


public interface ProductDao extends JpaRepository<Product, Integer> {

	@Query(value = "SELECT p.id, p.name, p.description, p.price, p.status, p.category_fk FROM cafesystem.product p , cafesystem.category c where p.category_fk=c.id\r\n"
			+ "", nativeQuery = true)
	List<Object> getAllProducts();
}
