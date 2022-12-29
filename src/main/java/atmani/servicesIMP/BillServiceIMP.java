package atmani.servicesIMP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import atmani.JWT.CustomerUsersDetailsService;
import atmani.constents.CafeConstants;
import atmani.dao.BillDao;
import atmani.model.Bill;
import atmani.services.BillService;
import atmani.utils.CafeUtils;

@Service
public class BillServiceIMP implements BillService {

	@Autowired
	BillDao billDao;

	Logger log = (Logger) LoggerFactory.getLogger(UserServiceIMP.class);

	@Autowired
	CustomerUsersDetailsService customerUsersDetailsService;

	@Override
	public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
		log.info("inside the method which generate the report");
		try {
			String fileName;
			System.out.println("1");
			if (validateRequestMap(requestMap)) {
				System.out.println("valide");
				if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
					System.out.println("contains");
					fileName = (String) requestMap.get("uuid");
				} else {
					fileName = CafeUtils.getUUID();
					requestMap.put("uuid", fileName);
					System.out.println("generate the new bill");
					insertBill(requestMap);
				}
				String data = "Name: " + requestMap.get("name") + "\n" + "Contact Number: "
						+ requestMap.get("contactNumber") + "\n" + "Email: " + requestMap.get("email") + "\n"
						+ "Payment Method: " + requestMap.get("paymentMethod");

				Document document = new Document();
				PdfWriter.getInstance(document,
						new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));

				document.open();
				setRectangleInPdf(document);

				Paragraph paragraph = new Paragraph("Cafe Management System", getFont("Header"));
				paragraph.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph);

				Paragraph paragraph2 = new Paragraph(data + "\n \n", getFont("Data"));
				document.add(paragraph2);

				PdfPTable table = new PdfPTable(5);
				table.setWidthPercentage(100);
				addTableHeader(table);

				JSONArray array = CafeUtils.getJsonDataFromString((String) requestMap.get("productDetail"));
				for (int i = 0; i < array.length(); i++) {
					addRows(table, CafeUtils.GetMapFromJson(array.getString(i)));
				}
				document.add(table);

				Paragraph footer = new Paragraph(
						"Total :" + requestMap.get("total") + "\n" + "Thank you for visiting. Please visit us again!",
						getFont("Data"));
				document.add(footer);
				document.close();
				return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);
			} else {
				System.out.println("BAD_REQUEST");
				return CafeUtils.getResponseEntity("Required data not found", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void addRows(PdfPTable table, Map<String, Object> data) {
		log.info("{inside addRows}");
		table.addCell((String) data.get("name"));
		table.addCell((String) data.get("category"));
		table.addCell((String) data.get("quantity"));
		table.addCell((Double.toString((Double) data.get("price"))));
		table.addCell((Double.toString((Double) data.get("total"))));
	}

	private void addTableHeader(PdfPTable table) {
		log.info("{inside addTableHeader}");
		Stream.of("Name", "Category", "Quantity", "Price", "Sub Total").forEach(columnTitel -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitel));
			header.setBackgroundColor(BaseColor.YELLOW);
			header.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.setVerticalAlignment(Element.ALIGN_CENTER);
			table.addCell(header);
		});

	}

	private Font getFont(String type) {
		log.info("inside Font");
		switch (type) {
		case "Header":
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
			headerFont.setStyle(Font.BOLD);
			return headerFont;

		case "Data":
			Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
			dataFont.setStyle(Font.BOLD);
			return dataFont;
		default:
			return new Font();
		}
	}

	private void setRectangleInPdf(Document document) throws DocumentException {
		log.info("inside RectanglePdf");
		Rectangle rectangle = new Rectangle(577, 825, 18, 15);
		rectangle.enableBorderSide(1);
		rectangle.enableBorderSide(2);
		rectangle.enableBorderSide(4);
		rectangle.enableBorderSide(8);
		rectangle.setBorderColor(BaseColor.BLACK);
		rectangle.setBorderWidthRight(1);
		document.add(rectangle);
	}

	private void insertBill(Map<String, Object> requestMap) {
		try {
			System.out.println(customerUsersDetailsService.getUserDetail().getName() + " username");
			Bill bill = new Bill();
			bill.setUuid((String) requestMap.get("uuid"));
			bill.setName((String) requestMap.get("name"));
			bill.setEmail((String) requestMap.get("email"));
			bill.setContactNumber((String) requestMap.get("contactNumber"));
			bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
			bill.setTotal(Integer.parseInt((String) requestMap.get("total")));

			bill.setProductDetail((String) requestMap.get("productDetail"));
			billDao.save(bill);
			bill.setCreatedBy(customerUsersDetailsService.getUserDetail().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private boolean validateRequestMap(Map<String, Object> requestMap) {
		return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
				&& requestMap.containsKey("email") && requestMap.containsKey("paymentMethod")
				&& requestMap.containsKey("productDetail") && requestMap.containsKey("total");
	}

	@Override
	public ResponseEntity<List<Bill>> getBills() {
		System.out.println(customerUsersDetailsService.getUserDetail().getName() + "--------------------------");
		List<Bill> list = new ArrayList<>();
		if (customerUsersDetailsService.getUserDetail().getRole().equals("admin")) {
			list = billDao.getAllBills();
			System.out.println(customerUsersDetailsService.getUserDetail().getName() + "--------------------------2");
		} else {
			list = billDao.getAllBillsByUsername(customerUsersDetailsService.getUserDetail().getName());
		}
		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
		log.info("inside getPdf : requestMap{}", requestMap);
		byte[] byteArray = new byte[0];
		try {
			
			System.out.println("1");
			if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap)) {
				System.out.println("2");
				return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
			}
			String filePath = CafeConstants.STORE_LOCATION + "\\" + (String) requestMap.get("uuid");
			if (CafeUtils.isFileExist(filePath)) {
				byteArray = getBytArray(filePath);
				System.out.println("path =>"+filePath);
				return new ResponseEntity<>(byteArray, HttpStatus.OK);
			} else {
				System.out.println("file doesn't exist");
				requestMap.put("isGenerate", false);
				generateReport(requestMap);
				byteArray = getBytArray(filePath);
				return new ResponseEntity<>(byteArray, HttpStatus.ACCEPTED);
			}

		} catch (Exception ex) {
			
		}
		return new ResponseEntity<>(byteArray, HttpStatus.OK);
	}

	private byte[] getBytArray(String filePath) throws Exception {
		// TODO Auto-generated method stub
		File initialeFile = new File(filePath);
		InputStream targetStream = new FileInputStream(initialeFile);
		byte[] byteArray = IOUtils.toByteArray(targetStream);
		targetStream.close();
		return byteArray;
	} 

	@Override
	public ResponseEntity<String> deleteBill(Integer id) {
		try {
			Optional<?> optional = billDao.findById(id);
			
			if (optional.isPresent()== true) {
				billDao.deleteById(id);
				return CafeUtils.getResponseEntity("Bill Deleted Successfully :)", HttpStatus.OK);
			} else {
				return CafeUtils.getResponseEntity("bill id not found :/", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
