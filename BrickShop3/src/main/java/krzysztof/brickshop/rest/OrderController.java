package krzysztof.brickshop.rest;

import krzysztof.brickshop.service.CustomerNotFoundException;
import krzysztof.brickshop.service.OrderDTO;
import krzysztof.brickshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{username}", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@PathVariable String username, @RequestBody String content) {
        return orderService.createOrder(username, Integer.parseInt(content));
    }

    @RequestMapping(value = "/{referenceId}", method = RequestMethod.PUT, produces = "application/json")
    public String updateOrder(@PathVariable String referenceId, @RequestBody String content) {
        return orderService.updateOrder(referenceId, Integer.parseInt(content));
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = "application/json")
    public List<OrderDTO> getCustomerOrders(@PathVariable String username) {
        return orderService.getCustomerOrders(username);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity customerDoesNotExist() {
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
