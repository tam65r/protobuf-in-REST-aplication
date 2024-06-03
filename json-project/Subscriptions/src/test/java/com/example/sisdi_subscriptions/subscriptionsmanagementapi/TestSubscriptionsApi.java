package com.example.sisdi_subscriptions.subscriptionsmanagementapi;

import com.example.sisdi_subscriptions.subscriptionmanagement.api.CreateSubscriptionRequest;
import com.example.sisdi_subscriptions.subscriptionmanagement.api.SubscriptionDTO;
import com.example.sisdi_subscriptions.subscriptionmanagement.model.Subscription;
import com.example.sisdi_subscriptions.testutils.JsonHelper;
import com.example.sisdi_subscriptions.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestSubscriptionsApi {
	private final MockMvc mockMvc;

	private final ObjectMapper objectMapper;

	private final Utils utils;

	String jwtToken ="eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJleGFtcGxlLmlvIiwic3ViIjoiNSxwYXBpbGxvbkBtYWlsLmNvbSIsImV4cCI6MTY5ODY1ODQ4MSwiaWF0IjoxNjk4NjIyNDgxLCJyb2xlcyI6IlNVQlNDUklCRVIifQ.JPU8Pgkgb4OTdKnCnSpx1hqIsK0EH61U9w_Uhcecpgrq6m3OuCu5NQ_hEFEuY6yO0rIfr3ggRFme4iQmE-Q9JhRPcJBT4HBzmrFwVPdrALUBq-IUJ0VpTQCmz5r_vPbsE006D6_HKBfUecXGTqgf6ynRc939-PfKJGqQL2K1ircajDvx_fP6TykVQTf9K7WftCBK3AgkB2HTzpdJnJOH8sU5XOGXwwckMYiLdWepDl4i1SzGhMnt4F6FJ600sw-cNpFf-pWdGmIFxiIiJUNf1eW9ZfzcPfkdsMvlXq_kc05MhqQhw9Tv1wYMjuB-JF13hHpRthv-ZGVN-loyc9xy_N0Gqbo8c8Djvk-K16iQdF-OvH0EsSoH1185ruvbD5JJmdFK1VdULQOu8KvUUYyPEpzB4T_aQmIYBlzJfOULcZHO827Z9LuNN5MG8aJL4JP_Y21whPF04lBa_kQFReJsqcpGdndi5oGtzyKkofELcPmnYuefV8rc7VZS45xSCm68DL7b2SN8QYk0jKshysn5SZDzW-AuzgSZ2bNFFsDfsJcRKFhsOD4bEP7-bJ1OhHhigzJRfx6xu53JwKe_cTOpbUofkieXpFGNuxoJNK_V0VzdtouZduqOTgwXgEabr6AwdS01gmt1fJProDn3aofsVSzJF0MEjsqd2w8U0bYfpTw";

	@Autowired
	public TestSubscriptionsApi(final MockMvc mockMvc, final ObjectMapper objectMapper, final Utils utils){
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.utils = utils;
	}

	@Test
	void getDetailsOfSubscriberPlan() throws Exception {
		//criar uma subscrição
		final CreateSubscriptionRequest initialRequest = new CreateSubscriptionRequest(
				"foo@mail.com",
				"Gold",
				"Annual",
				"Card",
				null
		);

		final MvcResult createResult = this.mockMvc.perform(post("/api/subscriptions/newSub")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonHelper.toJson(objectMapper, initialRequest)))
				.andExpect(status().isCreated())
				.andReturn();

		final SubscriptionDTO dto = JsonHelper.fromJson(objectMapper, createResult.getResponse().getContentAsString(),
				SubscriptionDTO.class);

		assertEquals(initialRequest.getUsername(),dto.getUsername(),"Name must be the safe!");
	}


	@Test
	void getDetailsByUsername() throws Exception{
		final MvcResult getResult = this.mockMvc.perform(get("/api/subscriptions/userPlanDetails")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		final String string = getResult.getResponse().getContentAsString();

		assertNotNull(string,"Plan is not null");
	}
}
