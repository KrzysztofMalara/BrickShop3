package krzysztof.brickshop.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderServiceTest {
    private static final String USERNAME = "krzysztof";

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldCreateOrder() {
        //given
        int content = 1000;

        //when
        String orderRereferenceId = orderService.createOrder(USERNAME, content);

        //then
        assertThat(orderRereferenceId).isNotBlank();
        assertTrue(orderRereferenceId.length() > 0);
    }
}