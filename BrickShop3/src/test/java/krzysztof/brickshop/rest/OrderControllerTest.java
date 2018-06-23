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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderControllerTest {
    private static final String KRZYSZTOF = "krzysztof";
    private static final String JOHN = "john";
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateOrdersAndReturnUniqueOrderReference() throws Exception {
        // given - when
        MvcResult result1 = performCreateOrder(KRZYSZTOF, 100);
        MvcResult result2 = performCreateOrder(KRZYSZTOF, 200);

        // then
        String orderReferenceId1 = result1.getResponse().getContentAsString();
        String orderReferenceId2 = result2.getResponse().getContentAsString();

        assertThat(orderReferenceId1).isNotEqualTo(orderReferenceId2);
    }

    private MvcResult performCreateOrder(String user, int content) throws Exception {
        return mockMvc.perform(post("/orders/" + user).content(Integer.toString(content)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andReturn();
    }


    @Test
    public void shouldGetSortedOrdersWithProperOrderReference() throws Exception {
        // given - when
        int brickCount1 = 100;
        MvcResult result1 = performCreateOrder(KRZYSZTOF, brickCount1);
        int brickCount2 = 200;
        MvcResult result2 = performCreateOrder(KRZYSZTOF, brickCount2);

        // then
        String orderReferenceId1 = result1.getResponse().getContentAsString();
        String orderReferenceId2 = result2.getResponse().getContentAsString();
        mockMvc.perform(get("/orders/" + KRZYSZTOF)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].orderReferenceId", is(orderReferenceId1)))
                .andExpect(jsonPath("$[0].bricksCount", is(brickCount1)))
                .andExpect(jsonPath("$[1].orderReferenceId", is(orderReferenceId2)))
                .andExpect(jsonPath("$[1].bricksCount", is(brickCount2)));
    }

    @Test
    public void shouldReturnEmptyListForNotExistingUser() throws Exception {
        mockMvc.perform(get("/orders/" + KRZYSZTOF)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }

    @Test
    public void shouldReturnOrdersWithReferencesAndBrickAmount() throws Exception {
        //given - when
        int brickCount1 = 100;
        MvcResult result1 = performCreateOrder(KRZYSZTOF, brickCount1);
        int brickCount2 = 200;
        MvcResult result2 = performCreateOrder(KRZYSZTOF, brickCount2);
        int brickCount3 = 300;
        MvcResult result3 = performCreateOrder(JOHN, brickCount3);
        int brickCount4 = 400;
        MvcResult result4 = performCreateOrder(JOHN, brickCount4);

        //then

        String orderReferenceId1 = result1.getResponse().getContentAsString();
        String orderReferenceId2 = result2.getResponse().getContentAsString();
        String orderReferenceId3 = result3.getResponse().getContentAsString();
        String orderReferenceId4 = result4.getResponse().getContentAsString();
        mockMvc.perform(get("/orders/" + KRZYSZTOF)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderReferenceId", is(orderReferenceId1)))
                .andExpect(jsonPath("$[0].bricksCount", is(brickCount1)))
                .andExpect(jsonPath("$[1].orderReferenceId", is(orderReferenceId2)))
                .andExpect(jsonPath("$[1].bricksCount", is(brickCount2)));

        mockMvc.perform(get("/orders/" + JOHN)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderReferenceId", is(orderReferenceId3)))
                .andExpect(jsonPath("$[0].bricksCount", is(brickCount3)))
                .andExpect(jsonPath("$[1].orderReferenceId", is(orderReferenceId4)))
                .andExpect(jsonPath("$[1].bricksCount", is(brickCount4)));
    }

    @Test
    public void shouldUpdateOrder() throws Exception {
        //given
        int brickCount1 = 100;
        MvcResult result1 = performCreateOrder(KRZYSZTOF, brickCount1);
        int updatedBrickCount = 300;
        String orderReferenceId1 = result1.getResponse().getContentAsString();

        //when
        mockMvc.perform(put("/orders/" + orderReferenceId1).content(Integer.toString(updatedBrickCount)))
                .andDo(print()).andExpect(status().isOk());

        //then
        mockMvc.perform(get("/orders/" + KRZYSZTOF)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderReferenceId", is(orderReferenceId1)))
                .andExpect(jsonPath("$[0].bricksCount", is(updatedBrickCount)));
    }
}