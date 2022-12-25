package atmani.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import atmani.model.Bill;
	
@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {

}
