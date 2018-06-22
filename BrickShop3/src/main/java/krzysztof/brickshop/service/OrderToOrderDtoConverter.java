package krzysztof.brickshop.service;

import krzysztof.brickshop.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class OrderToOrderDtoConverter {

    public List<OrderDTO> convertToOrderJson(List<Order> orders) {
        return orders.stream()
                .map(OrderToOrderDtoConverter::convertToOrderJson)
                .collect(Collectors.toList());
    }

    private static OrderDTO convertToOrderJson(Order order) {
        return new OrderDTO(order.getReferenceId(), order.getBrickCount());
    }
}
