package atmani.restIMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import atmani.constents.CafeConstants;
import atmani.model.Bill;
import atmani.restController.BillRest;
import atmani.services.BillService;
import atmani.utils.CafeUtils;

@RestController
public class BillRestIMP implements BillRest {

	@Autowired
	BillService billService;

	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		try {
			return billService.generateReport(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Bill>> getBills() {
		try {
				return billService.getBills();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
