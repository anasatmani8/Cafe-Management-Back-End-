package atmani.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import atmani.model.Bill;

@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {

	@Query(value = "SELECT * FROM cafesystem.bill order by id desc", nativeQuery = true)
	List<Bill> getAllBills();

	@Query(value = "SELECT * FROM cafesystem.bill where email=:username order by id desc", nativeQuery = true)
	List<Bill> getAllBillsByUsername(@Param("username") String name);

}
