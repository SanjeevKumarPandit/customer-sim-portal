package telecommunication.customersimportal.response;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class EmailNotificationResponse {
	
	private String status;
	private String message;
}
