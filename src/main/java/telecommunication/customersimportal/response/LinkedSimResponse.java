package telecommunication.customersimportal.response;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LinkedSimResponse {

	private String status;
	private String message;
}