package krzysztof.brickshop.service;

import krzysztof.brickshop.dao.CustomerDao;
import krzysztof.brickshop.dao.OrderDao;
import krzysztof.brickshop.model.Customer;
import krzysztof.brickshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class OrderService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private OrderDao orderDao;

    public OrderDTO createOrder(String customerName, int brickCount)  {

        Customer customer = customerDao.findByName(customerName);
        if (customer == null) {
            customer = saveCustomer(customerName);
        }

        Order order = new Order(brickCount, new Date().getTime());
        Order saved = orderDao.save(order);
        return messageConverter.convertToMessageJson(saved);
    }
    private Customer saveCustomer(String username) {
        Customer customer = new Customer(username);
        return customerDao.save(customer);
    }

}