package telecommunication.customersimportal.response;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import telecommunication.customersimportal.model.SimDtl;

@Component
@Data
public class GetCustomerSimResponse {

	private String status;
	private String message;
	private String customerName;
	private List<SimDtl> mappedSimList;
}
