package telecommunication.customersimportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telecommunication.customersimportal.model.CustomerDtl;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDtl, Integer> {

	CustomerDtl findByCustomerDobAndUidNo(@Param("customerDob") String customerDob, @Param("uidNo") String uidNo);
	
	@Query("select count(*) from CustomerDtl where customerDob= :customerDob and uidNo = :uidNo")
	public Integer getCustomerCount(@Param("customerDob") String customerDob, @Param("uidNo") String uidNo);
	
	@Query("select customerId from CustomerDtl where customerDob= :customerDob and uidNo = :uidNo")
	public Integer getCustomerId(@Param("customerDob") String customerDob, @Param("uidNo") String uidNo);
	
	// update Only Name,EMail, Address
	@Transactional
	@Modifying
	@Query("UPDATE CustomerDtl set customerName= :customerName, customerEmail= :customerEmail,address= :address "
			+ "WHERE  customer_id= :customerId ")
	public void UpdateExistingCustomer(@Param("customerName") String customerName,
			@Param("customerEmail") String customerEmail, @Param("address") String address,
			@Param("customerId") Integer customerId);
	
	CustomerDtl findByCustomerId(@Param("customerId") Integer customerId);
	
}
