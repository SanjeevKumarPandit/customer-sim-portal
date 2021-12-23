package telecommunication.customersimportal.request;

import lombok.Data;

@Data
public class GetCustomerSimRequest {
	
	private String uidNo;
	private String customerDob;
}
