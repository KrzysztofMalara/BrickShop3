package krzysztof.brickshop.rest;

import krzysztof.brickshop.service.OrderDTO;
import krzysztof.brickshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages/{username}")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@PathVariable String username, @RequestBody String content) {
        return orderService.createOrder(username, content);
    }

}
