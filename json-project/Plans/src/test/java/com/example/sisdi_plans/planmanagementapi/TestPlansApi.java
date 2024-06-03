package com.example.sisdi_plans.planmanagementapi;

import com.example.sisdi_plans.planmanagement.api.CreatePlanRequest;
import com.example.sisdi_plans.planmanagement.api.EditPlanRequest;
import com.example.sisdi_plans.planmanagement.api.PlanDTO;
import com.example.sisdi_plans.planmanagement.service.PlanService;
import com.example.sisdi_plans.testutils.JsonHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestPlansApi {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    String jwtToken = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJleGFtcGxlLmlvIiwic3ViIjoiMSxyaXZlckBtYWlsLmNvbSIsImV4cCI6MTY5ODY1NTM4NCwiaWF0IjoxNjk4NjE5Mzg0LCJyb2xlcyI6Ik1BUktFVElOR19ESVJFQ1RPUiJ9.ATy7PDT_Z6DDmpW70iuvmyDW-K4lh7s_EvKfLPQBWGb092QiVzNuzq39IHaRePpixpG3EzWvtaGaAmmz6TG4hF-osjWX19O-Cd2NriBwJVbzpZFNvwa_7q9R9YtamxTZJ2bcdtCzS3Ek676DFZTxhJdXbf7jShy0uffoW-3JNu4DRZcIu6llZ9vb3oD8N2RhK1jRwl0HXlOOxrdnwoLdBMjMZIXhvxFATocVNyzuedthp-4HDHfSumZ-K-d7dHyRPfhhStbPFJBRiAcb4G4Sz5PEVkMv2rLMhqyBfXkJeMCI7V9gvKWH1HLC2YkL2rlCKqhDlvl-NyWvNXKZq9TC0oLGmw5-2Yz7AiF1CMIrUcBSkwPDVK_p9nmPf2hXMZfNmFNlZ61BOI0j_T4tjAFAnUUg164XKoQzrWuqHnRSm51_99lXklXufr-CM_zBen3JIxaVR0KXT-zARRdoHtmThr0FgG-MDjxYqTHQylUJDUa28l0EDN8GbmlyeSd2YVyTyMkTGRFbbLx19dqRqomP__woR3DB5LvcPT6VvCcpelekbwcHshn9-U1vd4Th4Hhng7ScxQdcUhUgMALfjQJeU0bSVAO0H4uH7Tgyw_IebGjrDE5LpyHDCFH4CNK6Dz6N10VNKcUcsUrhZMibciEVtpUMKQsryEURUdkikNwHQUY";

    @Autowired
    public TestPlansApi(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }


    @Test
    void testGetAllPlans() throws Exception{
        //mostra ao utilizador todos os planos ativos
        final MvcResult getResult = this.mockMvc.perform(get("/api/plans/all")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        final Iterable<PlanDTO> plans = objectMapper.readValue(
                getResult.getResponse().getContentAsString(),
                new TypeReference<Iterable<PlanDTO>>() {}
        );

        for (PlanDTO plan : plans) {
            assertNotNull(plan.getName(),"Plan name cant be null");
            assertEquals(true, plan.isActive(), "Plan must be active otherwise it can't be choose");
        }
    }

    @Test
    void testGetAllActivePlansv2() throws Exception{

        // desativo pelo id, id=1 é o id do plano Free
        final MvcResult patchResult = this.mockMvc.perform(patch("/api/plans/deactivate/Free")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        //mostra ao utilizador todos os planos ativos
        final MvcResult getResult = this.mockMvc.perform(get("/api/plans/all")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        final Iterable<PlanDTO> plans = objectMapper.readValue(
                getResult.getResponse().getContentAsString(),
                new TypeReference<Iterable<PlanDTO>>() {}
        );

        for (PlanDTO plan : plans) {
            assertNotNull(plan.getName(),"Plan name cant be null");
            assertNotEquals("Free",plan.getName());
            assertEquals(true, plan.isActive(), "Plan must be active otherwise it can't be choose");
        }
    }

    @Test
    void testDefinePlanSucess() throws Exception{
        final CreatePlanRequest goodRequest = new CreatePlanRequest(
                "TestPlan3",
                "This Plan is designed for tests",
                15,
                "Automatic",
                1000,
                (float) 8.49,
                (float) 84.90
        );

        final MvcResult createResult = this.mockMvc.perform(post("/api/plans/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isCreated()).andReturn();

        final PlanDTO dto = JsonHelper.fromJson(objectMapper, createResult.getResponse().getContentAsString(),
                PlanDTO.class);
        assertNotNull(dto.getName(), "Plan name must not be null!");
    }

    @Test
    void testPromotePlan() throws Exception {
        MvcResult promoteResult = this.mockMvc.perform(patch("/api/plans/deactivate/TestPlan3")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String responseContent = promoteResult.getResponse().getContentAsString();
        final PlanDTO plan = JsonHelper.fromJson(objectMapper, responseContent, PlanDTO.class);

        assertEquals("TestPlan3", plan.getName(), "Incorrect plan name!");
    }


    @Test
    void testChangeDescription() throws Exception{
        final CreatePlanRequest goodRequest = new CreatePlanRequest(
                "TestPlan6",
                "This Plan is designed for tests",
                15,
                "Automatic",
                1000,
                (float) 8.49,
                (float) 84.90
        );

        final MvcResult createResult = this.mockMvc.perform(post("/api/plans/create")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, goodRequest)))
                .andExpect(status().isCreated()).andReturn();

        final PlanDTO dto = JsonHelper.fromJson(objectMapper, createResult.getResponse().getContentAsString(),
                PlanDTO.class);



        final EditPlanRequest request = new EditPlanRequest(
                "O plano foi alterado corretamente!",
                null,
                null,
                null
        );


        final MvcResult patchResult = this.mockMvc.perform(patch("/api/plans/" + dto.getName())
                        .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON).content(JsonHelper.toJson(objectMapper, request)))
                .andExpect(status().isOk()).andReturn();

        final PlanDTO planDTO = JsonHelper.fromJson(objectMapper, patchResult.getResponse().getContentAsString(),
                PlanDTO.class);

        assertEquals(request.getDescription(),planDTO.getDescription(),"Plan description changed!");
        assertNotEquals(dto.getDescription(), planDTO.getDescription(),"New description its not the same as the old one!");
    }
}
