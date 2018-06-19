package krzysztof.brickshop.dao;

import krzysztof.brickshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    Customer findByName(String customerName);
}
