package atmani.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import atmani.model.Bill;

public interface BillService {

	ResponseEntity<String> generateReport(Map<String, Object> requestMap);

	ResponseEntity<List<Bill>> getBills();

}
