package atmani.servicesIMP;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import atmani.dao.BillDao;
import atmani.dao.CategoryDao;
import atmani.dao.ProductDao;
import atmani.dao.UserDao;
import atmani.services.DashboardService;

@Service
public class DashboardServiceIMP implements DashboardService {

	@Autowired
	ProductDao productDao;

	@Autowired
	BillDao billDao;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	UserDao userDao;

	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		Map<String, Object> map = new HashMap<>();
		map.put("category", categoryDao.count());
		map.put("product", productDao.count());
		map.put("bill", billDao.count());
		map.put("user", userDao.count());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
