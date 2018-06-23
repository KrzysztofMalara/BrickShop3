package krzysztof.brickshop.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderControllerTest {
    private static final String ORDERS_KRZYSZTOF = "/orders/krzysztof";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateOrdersAndReturnUniqueOrderReference() throws Exception {
        // given - when
        MvcResult result1 = performCreateOrder(100);
        MvcResult result2 = performCreateOrder(200);

        // then
        String orderReferenceId1 = result1.getResponse().getContentAsString();
        String orderReferenceId2 = result2.getResponse().getContentAsString();

        assertThat(orderReferenceId1).isNotEqualTo(orderReferenceId2);
    }

    private MvcResult performCreateOrder(int content) throws Exception {
        return mockMvc.perform(post(ORDERS_KRZYSZTOF).content(Integer.toString(content)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();
    }


    @Test
    public void shouldGetSortedOrdersWithProperOrderReference() throws Exception {
        // given - when
        int brickCount1 = 100;
        MvcResult result1 = performCreateOrder(brickCount1);
        int brickCount2 = 200;
        MvcResult result2 = performCreateOrder(brickCount2);

        // then
        String orderReferenceId1 = result1.getResponse().getContentAsString();
        String orderReferenceId2 = result2.getResponse().getContentAsString();
        mockMvc.perform(get(ORDERS_KRZYSZTOF)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].orderReferenceId", is(orderReferenceId1)))
                .andExpect(jsonPath("$[0].bricksCount", is(brickCount1)))
                .andExpect(jsonPath("$[1].orderReferenceId", is(orderReferenceId2)))
                .andExpect(jsonPath("$[1].bricksCount", is(brickCount2)));
    }

    @Test
    public void shouldReturnEmptyListForNotExistingUser() throws Exception {
        mockMvc.perform(get(ORDERS_KRZYSZTOF)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }


}