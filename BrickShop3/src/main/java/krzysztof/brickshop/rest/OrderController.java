package krzysztof.brickshop.rest;

import krzysztof.brickshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/{username}")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@PathVariable String username, @RequestBody String content) {
        return orderService.createOrder(username, Integer.parseInt(content));
    }

}
