package telecommunication.customersimportal.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import telecommunication.customersimportal.request.CustomerRequest;
import telecommunication.customersimportal.request.GetCustomerSimRequest;
import telecommunication.customersimportal.request.LinkedSimRequest;
import telecommunication.customersimportal.request.SimRequest;
import telecommunication.customersimportal.response.CustomerResponse;
import telecommunication.customersimportal.response.EmailNotificationResponse;
import telecommunication.customersimportal.response.GetCustomerResponse;
import telecommunication.customersimportal.response.GetCustomerSimResponse;
import telecommunication.customersimportal.response.GetSimResponse;
import telecommunication.customersimportal.response.LinkedSimResponse;
import telecommunication.customersimportal.response.SimResponse;
import telecommunication.customersimportal.service.CustomerService;

@RestController
@RequestMapping("/telecommunication")
public class CustomerResource {

	@Autowired
	CustomerService service;

	// Create Customer API of Telecommunication
	@RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
	public CustomerResponse saveUpdateCustomer(@RequestBody CustomerRequest request) {
		CustomerResponse response = new CustomerResponse();
		response = service.saveUpdateCustomer(request);
		return response;
	}

	// Create SIM API of Telecommunication
	@RequestMapping(path = "/createSim", method = RequestMethod.POST)
	public SimResponse saveUpdateSim(@RequestBody SimRequest request) {
		SimResponse response = new SimResponse();
		response = service.saveUpdateSim(request);
		return response;
	}

	// get All created SIM Details API of Telecommunication
	@RequestMapping(path = "/getAllSimDtl", method = RequestMethod.GET)
	public GetSimResponse getAllSimDtl() {
		GetSimResponse response = new GetSimResponse();
		response = service.getAllSimDtl();
		return response;
	}

	// get Customer SIM Details API of Telecommunication
	@RequestMapping(path = "/getCustomerSimDtl", method = RequestMethod.GET)
	public GetCustomerSimResponse getCustomerSimDtl(@RequestBody GetCustomerSimRequest request) {
		GetCustomerSimResponse response = new GetCustomerSimResponse();
		response = service.getCustomerSimDtl(request);
		return response;
	}

	// Linked SIM to Customer API of Telecommunication
	@RequestMapping(path = "/linkSim", method = RequestMethod.POST)
	public LinkedSimResponse linkedSimToCustomer(@RequestBody LinkedSimRequest request) {
		LinkedSimResponse response = new LinkedSimResponse();
		response = service.linkedSimToCustomer(request);
		return response;
	}

	// Birthday Email Notification API of Telecommunication
	@RequestMapping(path = "/emailNotification", method = RequestMethod.GET)
	public EmailNotificationResponse sendEmailNotification() {
		EmailNotificationResponse response = new EmailNotificationResponse();
		response = service.sendEmailNotification();
		return response;
	}
	
	// get list of customer API
	@RequestMapping(path = "/getCustomerDtl", method = RequestMethod.GET)
	public GetCustomerResponse getCustomerDtl() {
		GetCustomerResponse response = new GetCustomerResponse();
		response = service.getCustomerDtl();
		return response;
	}

}
