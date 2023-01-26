package atmani.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import atmani.model.Product;
 //SELECT id FROM cafesystem.product where category_fk=1;
@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Integer> {

	@Query(value = "SELECT * FROM cafesystem.product p , cafesystem.category c where p.category_fk=c.id\r\n"
			+ "", nativeQuery = true)
	List<Product> getAllProducts();

	@Modifying
	@Query(value = "UPDATE Product p SET p.status =:status WHERE p.id =:id", nativeQuery = true)
	Integer updateStatus(@Param("status") String status, @Param("id") int id);

	@Query(value = "SELECT * FROM cafesystem.product p  where p.category_fk=:id and p.status='true'", nativeQuery = true)
	List<Product> getProdsByCat(@Param("id") int id);

	@Query(value = "SELECT * FROM cafesystem.product p  where p.id=:id", nativeQuery = true)
	List<Product> getProdsById(@Param("id") int id);
	
	@Query(value = "SELECT id FROM cafesystem.product where category_fk=:id", nativeQuery = true)
	List<String> getIdProdsByCat(@Param("id") int id);
}
