package krzysztof.brickshop.service;

import krzysztof.brickshop.dao.CustomerDao;
import krzysztof.brickshop.dao.OrderDao;
import krzysztof.brickshop.model.Customer;
import krzysztof.brickshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderToOrderDtoConverter orderConverter;

    public String createOrder(String customerName, int brickCount) {

        Customer customer = customerDao.findByName(customerName);
        if (customer == null) {
            customer = saveCustomer(customerName);
        }

        Order order = new Order(brickCount, new Date().getTime(), UUID.randomUUID().toString(), customer);
        Order saved = orderDao.save(order);
        return saved.getReferenceId();
    }

    private Customer saveCustomer(String username) {
        Customer customer = new Customer(username);
        return customerDao.save(customer);
    }

    public List<OrderDTO> getCustomerOrders(String customerName) {
        Customer customer = customerDao.findByName(customerName);
        if (customer == null) {
            throw new CustomerNotFoundException(customerName);
        }
        return orderConverter.convertToOrderJson(customer.getOrders());
    }

    public String updateOrder(String referenceId, int brickCount) {
        Order order = orderDao.findByReferenceId(referenceId);
        order.setBrickCount(brickCount);
        orderDao.save(order);
        return referenceId;
    }
}
