package telecommunication.customersimportal.response;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import telecommunication.customersimportal.model.CustomerDtl;
import telecommunication.customersimportal.model.SimDtl;

@Component
@Data
public class GetCustomerResponse {
	
	private String status;
	private String message;
	private List<CustomerDtl> customerList;
	private List<SimDtl> mappedSimList;
}
