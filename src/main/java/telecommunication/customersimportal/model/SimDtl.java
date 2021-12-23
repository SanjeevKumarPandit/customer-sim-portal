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

@XmlRootElement(name = "SimDtl")
@Entity
@Table(name = "sim_dtl")
@Data
public class SimDtl implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SIM_ID", unique = true, nullable = false)
	private Integer simId;

	@Column(name = "COMPANY_NAME")
	private String companyName;

	@Column(name = "ACTIVATION_DATE")
	private String activationDate;

	@Column(name = "SIM_STATUS")
	private String simStatus;

	@Column(name = "CUSTOMER_ID")
	private Integer customerId;
}
