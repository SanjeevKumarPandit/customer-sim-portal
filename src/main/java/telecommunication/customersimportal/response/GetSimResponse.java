package telecommunication.customersimportal.response;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import telecommunication.customersimportal.model.SimDtl;

@Component
@Data
public class GetSimResponse {
	
	private String status;
	private String message;
	private List <SimDtl> simList;
}
