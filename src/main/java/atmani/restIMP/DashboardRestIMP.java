package atmani.restIMP;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import atmani.restController.DashboardRest;
import atmani.services.DashboardService;

@RestController
public class DashboardRestIMP implements DashboardRest {
	
	@Autowired
	DashboardService dashboardService;

	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		// TODO Auto-generated method stub
		return dashboardService.getCount();
	}

}
