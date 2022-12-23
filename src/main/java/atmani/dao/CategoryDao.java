package atmani.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import atmani.model.Category;

@Repository
@Transactional
public interface CategoryDao extends JpaRepository<Category, Integer> {

	@Query(value = "SELECT * FROM cafesystem.category c where c.id in (SELECT p.category_fk FROM cafesystem.product p where p.status='true')", nativeQuery = true)
	// SELECT c.name FROM cafesystem.category c, cafesystem.product p where
	// c.id=p.category_fk and p.status='true'
	List<Category> getAllCategories();
}
