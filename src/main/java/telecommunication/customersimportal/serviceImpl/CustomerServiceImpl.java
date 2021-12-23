package telecommunication.customersimportal.serviceImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telecommunication.customersimportal.model.CustomerDtl;
import telecommunication.customersimportal.model.SimDtl;
import telecommunication.customersimportal.repository.CustomerRepository;
import telecommunication.customersimportal.repository.SimRepository;
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
import telecommunication.customersimportal.util.SendMailAPI;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Value("${spring.mail.username}")
	private String userEmail;
	
	@Value("${spring.mail.password}")
	private  String userPassword;
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	SimRepository simRepository;

	@Override
	public CustomerResponse saveUpdateCustomer(CustomerRequest request) {
		CustomerResponse response = new CustomerResponse();
		try {
			CustomerDtl customerEntity = request.getCustomerEntity();
			if(StringUtils.isNotBlank(customerEntity.getCustomerDob()) &&(StringUtils.isNotBlank(customerEntity.getUidNo()))) {
				Integer count = customerRepository.getCustomerCount(customerEntity.getCustomerDob(),customerEntity.getUidNo());
				Integer customerId = customerRepository.getCustomerId(customerEntity.getCustomerDob(),customerEntity.getUidNo());
				if (count > 0 ){
					// update Only Name,EMail, Address In Case Existing Customer
					customerRepository.UpdateExistingCustomer(customerEntity.getCustomerName(),
							customerEntity.getCustomerEmail(), customerEntity.getAddress(), customerId);
					response.setStatus("0");
					response.setMessage("Customer is already Created..!! Update Only Name,EMail & Address ");
				} else {
					customerRepository.save(customerEntity);
					response.setStatus("1");
					response.setMessage("Data Saved Successfully");
					response.setCustomerId(customerEntity.getCustomerId());
				}
			}else {
				response.setStatus("0");
				response.setMessage("Customer Dob and UID no can't be null..!!");
			}
		} catch (Exception ex) {
			response.setStatus("0");
			response.setMessage("Some Unknown Exception occured" + ex.getMessage());
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	public SimResponse saveUpdateSim(SimRequest request) {
		SimResponse response = new SimResponse();
		try {
			SimDtl simEntity = request.getSimEntity();
			simEntity.setActivationDate(getCurrentDate());
			simEntity.setSimStatus("A");
			simRepository.save(simEntity);
			response.setStatus("1");
			response.setMessage("Data Saved Successfully");
			response.setSimId(simEntity.getSimId());
		} catch (Exception ex) {
			response.setStatus("0");
			response.setMessage("Unknown Exception occured" + ex.getMessage());
			ex.printStackTrace();
		}
		return response;
	}

	@Override
	public GetSimResponse getAllSimDtl() {
		GetSimResponse response = new GetSimResponse();
		List<SimDtl> simList = simRepository.findAll();
		if (simList != null && simList.size() > 0) {
			response.setStatus("1");
			response.setMessage("Success");
			response.setSimList(simList);
		} else {
			response.setStatus("0");
			response.setMessage("Data Not Found..!!!");
		}
		return response;
	}

	@Override
	public GetCustomerSimResponse getCustomerSimDtl(GetCustomerSimRequest request) {
		GetCustomerSimResponse response = new GetCustomerSimResponse();
		String customerDob = request.getCustomerDob();
		String uidNo = request.getUidNo();
		CustomerDtl CustomerDtl = customerRepository.findByCustomerDobAndUidNo(customerDob, uidNo);
		if (CustomerDtl != null) {
			List<SimDtl> mappedSimList = simRepository.findByCustomerIdAndSimStatus(CustomerDtl.getCustomerId(), "L");
			if (mappedSimList != null && mappedSimList.size() > 0) {
				response.setStatus("1");
				response.setMessage("Success");
				response.setCustomerName(CustomerDtl.getCustomerName());
				response.setMappedSimList(mappedSimList);
			} else {
				response.setStatus("0");
				response.setMessage("Sim is not registered for this customer");
			}
		} else {
			response.setStatus("0");
			response.setMessage("Customer Not Exist");
		}
		return response;
	}

	@Override
	public LinkedSimResponse linkedSimToCustomer(LinkedSimRequest request) {
		LinkedSimResponse response = new LinkedSimResponse();
		String customerDob = request.getCustomerDob();
		String uidNo = request.getUidNo(); 
		Integer customerId = customerRepository.getCustomerId(customerDob, uidNo);
		if (customerId != null) {
			Integer simCount = simRepository.getSIMCount("A");
			if(simCount>0) {
				// Link SIM with customer and set SIM Status is L also while sim is linked with customer 
				//IF SIM Status is A i.e. SIM IS ACTIVATED and SIM STATUS is L i.e. SIM IS LINKED
				simRepository.LinkedSimToCustomer(customerId, "L");
				response.setStatus("1");
				response.setMessage("Customer has been Linked to SIM");
			}else {
				response.setStatus("0");
				response.setMessage("SIM is not available, Pleae First Create SIM");
			}
		} else {
			response.setStatus("0");
			response.setMessage("Customer Not Exist");
		}
		return response;
	}
	
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
	
	public static int daysRemain(LocalDate dob) {
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		long age = ChronoUnit.YEARS.between(dob, today);
		LocalDate nextBirthday = dob.plusYears(age);
		if (nextBirthday.isBefore(today)) {
			nextBirthday = dob.plusYears(age + 1);
		}
		long daysUntilNextBirthday = ChronoUnit.DAYS.between(today, nextBirthday);
		return Math.toIntExact(daysUntilNextBirthday);
	}

	@Override
	public EmailNotificationResponse sendEmailNotification() {
		EmailNotificationResponse response = new EmailNotificationResponse();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int sendMailCount = 0;
		int notSendMailCount = 0;
		// get linked Customer SIM Details
		List<SimDtl> simList = simRepository.findBySimStatus("L");
		for (SimDtl simDtl : simList) {
			CustomerDtl customerDtl = customerRepository.findByCustomerId(simDtl.getCustomerId());
			String customerDob = customerDtl.getCustomerDob();
			LocalDate localDate = LocalDate.parse(customerDob, dateFormat);
			final long days = daysRemain(localDate);
			System.out.println("Days between: " + days);
			// send Birthday Notification to customer through mail..
			if (7 >= days) {
				String text = "Dear " + customerDtl.getCustomerName()
						+ ",<br> <br> This is your birthday notification your birthday is coming within 7 days.";
				String senderMailId = customerDtl.getCustomerEmail();
				try {
					SendMailAPI.sendmail(senderMailId, text,userEmail,userPassword);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				sendMailCount++;
			} else {
				System.out.println("out of Days: " + days);
				notSendMailCount++;
			}
		}
		response.setStatus("1");
		response.setMessage("Total  " + sendMailCount + " Notification Mail send to customer &&" + " Birthday of "
				+ notSendMailCount + " Customer is not within seven days");
		return response;
	}

	@Override
	public GetCustomerResponse getCustomerDtl() {
		GetCustomerResponse response = new GetCustomerResponse();
		int birthdayCelebrateCount = 0;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<CustomerDtl> customerList = customerRepository.findAll();
		List<CustomerDtl> custDtls = new ArrayList<CustomerDtl>();
		List<SimDtl> mappedSimList = null;
		for (CustomerDtl custDtl : customerList) {
			String customerDob = custDtl.getCustomerDob();
			LocalDate localDate = LocalDate.parse(customerDob, dateFormat);
			final long days = daysRemain(localDate);
			if (days == 0) {
				mappedSimList = simRepository.findByCustomerIdAndSimStatus(custDtl.getCustomerId(), "L");
				custDtls.add(custDtl);
				birthdayCelebrateCount++;
			}
		}
		response.setStatus("1");
		response.setMessage("TODAY TOTAL " + birthdayCelebrateCount + " CUSTOMER CELEBRATE OWN BIRTHDAY..!! ");
		response.setCustomerList(custDtls);
		response.setMappedSimList(mappedSimList);
		return response;
	}

}
