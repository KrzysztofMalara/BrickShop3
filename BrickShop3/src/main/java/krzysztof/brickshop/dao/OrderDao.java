package krzysztof.brickshop.dao;

import krzysztof.brickshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {
}
