package telecommunication.customersimportal.service;

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

public interface CustomerService {

	CustomerResponse saveUpdateCustomer(CustomerRequest request);

	SimResponse saveUpdateSim(SimRequest request);

	GetSimResponse getAllSimDtl();

	GetCustomerSimResponse getCustomerSimDtl(GetCustomerSimRequest request);

	LinkedSimResponse linkedSimToCustomer(LinkedSimRequest request);

	EmailNotificationResponse sendEmailNotification();

	GetCustomerResponse getCustomerDtl();

}
