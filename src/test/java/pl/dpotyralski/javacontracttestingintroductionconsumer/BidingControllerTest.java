package pl.dpotyralski.javacontracttestingintroductionconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(ids = {"pl.dpotyralski:java-contract-testing-introduction-producer:+:stubs:8081"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class BidingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BidingController bidingController;

    @Test
    void shouldCreateBidSuccessfully() throws Exception {
        mockMvc.perform(post("/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BidRequest(10, "IBM"))))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(new BidResponse(BidResponseStatus.BID_CREATED))));
    }

    @Test
    void shouldRejectBidBecauseOfRisk() throws Exception {
        mockMvc.perform(post("/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BidRequest(15, "Microsoft"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new BidResponse(BidResponseStatus.BID_REJECTED))));
    }

}