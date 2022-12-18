package atmani.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import atmani.model.Category;

@Repository
@Transactional
public interface CategoryDao extends JpaRepository<Category, Integer> {

}
