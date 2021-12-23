package telecommunication.customersimportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telecommunication.customersimportal.model.SimDtl;

@Repository
public interface SimRepository extends JpaRepository<SimDtl, Integer> {

	public List<SimDtl> findByCustomerIdAndSimStatus(@Param("customerId") Integer CustomerId,
			@Param("simStatus") String simStatus);

	@Transactional
	@Modifying
	@Query(value = "UPDATE SIM_DTL SET SIM_STATUS= :simStatus, customer_id= :customerId WHERE  SIM_STATUS= 'A' and customer_id is null limit 1", nativeQuery = true)
	public void LinkedSimToCustomer(@Param("customerId") Integer customerId, @Param("simStatus") String simStatus);
	
	@Query("select count(*) from SimDtl where simStatus= :simStatus ")
	public int getSIMCount(@Param("simStatus") String simStatus);
	
	public List<SimDtl> findBySimStatus(@Param("simStatus") String simStatus);
}
