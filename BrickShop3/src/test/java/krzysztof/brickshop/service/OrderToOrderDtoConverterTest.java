package krzysztof.brickshop.service;

import krzysztof.brickshop.model.Customer;
import krzysztof.brickshop.model.Order;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderToOrderDtoConverterTest {

    private final OrderToOrderDtoConverter orderConverter = new OrderToOrderDtoConverter();


    @Test
    public void shouldConvertOrders() {
        //given
        int bricksCount1 = 100;
        String orderReferenceId1 = "123abc";
        String firstCustomerName = "customer";
        long dateTimestamp = 12345L;
        Order first = createOrder(bricksCount1, firstCustomerName, dateTimestamp, orderReferenceId1);
        long secondDateTimestamp = new Date().getTime();

        int bricksCount2 = 200;
        String orderReferenceId2 = "123def";
        String secondCustomerName = "another Customer";
        Order second = createOrder(bricksCount2, secondCustomerName, secondDateTimestamp, orderReferenceId2);


        OrderDTO firstExpected = new OrderDTO(orderReferenceId1, bricksCount1);
        OrderDTO secondExpected = new OrderDTO(orderReferenceId2, bricksCount2);

        //when
        List<OrderDTO> result = orderConverter.convertToOrderJson(Arrays.asList(first, second));

        //then
        assertThat(result).containsExactly(firstExpected, secondExpected);
    }

    private static Order createOrder(int brickCount, String customerName, long dateTimestamp, String orderReference) {
        Customer customer = new Customer(customerName);
        return new Order(brickCount, dateTimestamp, orderReference, customer);
    }
}