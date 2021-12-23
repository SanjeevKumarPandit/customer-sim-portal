package telecommunication.customersimportal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "CustomerDtl")
@Entity
@Table(name = "customer_dtl")
@Data
public class CustomerDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CUSTOMER_ID", unique = true, nullable = false)
	private Integer customerId;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "FATHER_NAME")
	private String fatherName;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "CUSTOMER_DOB")
	private String customerDob;

	@Column(name = "CONTACT_NO")
	private String contactNo;

	@Column(name = "UID_NO")
	private String uidNo;

	@Column(name = "VOTER_ID")
	private String voterId;
	
	
	@Column(name = "CUSTOMER_EMAIL")
	private String customerEmail;
	
	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DISTRICT")
	private String district;

	@Column(name = "STATE")
	private String state;

	@Column(name = "PINCODE")
	private String pincode;
}
