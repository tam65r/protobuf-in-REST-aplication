package com.example.sisdi_plans.planmanagementapi;

import com.example.sisdi_plans.exceptions.InconsistencyDataException;
import com.example.sisdi_plans.planmanagement.model.MusicSuggestions;
import com.example.sisdi_plans.planmanagement.model.PlanJPA;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlansTest {

    private PlanJPA planJPA;

    private PlansTest(){
        planJPA = new PlanJPA("TestPlan", "Description", 100, MusicSuggestions.AUTOMATIC, 10, 10.0f, 100.0f);
    }

    @Test
    public void testPlanConstructor() {
        PlanJPA testPlanJPA = new PlanJPA("TestPlan", "Description", 100, MusicSuggestions.AUTOMATIC, 10, 10.0f, 100.0f);
        assertEquals("TestPlan", testPlanJPA.getName());
        assertEquals("Description", testPlanJPA.getDescription());
        assertEquals(100, testPlanJPA.getNumberOfMinutes());
        assertEquals(MusicSuggestions.AUTOMATIC, testPlanJPA.getMusicSuggestions());
        assertEquals(10, testPlanJPA.getMusicCollections());
        assertEquals(10.0f, testPlanJPA.getMonthlyFee(), 0.01f);
        assertEquals(100.0f, testPlanJPA.getAnnualFee(), 0.01f);
        assertEquals(1, testPlanJPA.getMaxNumberDevices());
        assertTrue(testPlanJPA.getIsActive());
    }

    @Test
    public void testChangeActivityStatus() {
        planJPA.setActive(true);
        planJPA.changeActivityStatus();
        assertFalse(planJPA.getIsActive());
    }

    @Test
    public void testChangeActivityStatusWhenAlreadyInactive() {
        PlanJPA planJPA = mock(PlanJPA.class);
        when(planJPA.getName()).thenReturn("TestPlan");
        when(planJPA.getIsActive()).thenReturn(false);

        doThrow(new InconsistencyDataException(PlanJPA.class, "TestPlan", "Plan is already inactive")).when(planJPA).changeActivityStatus();

        try {
            planJPA.changeActivityStatus();
        } catch (InconsistencyDataException e) {
            assertEquals("Data from Plan with id TestPlan is inconsistent: Plan is already inactive", e.getMessage());
        }
    }

    @Test
    public void testApplyPatch() {
        planJPA.applyPatch("New Description", "50", "5", "PERSONALIZED");
        assertEquals("New Description", planJPA.getDescription());
        assertEquals(50, planJPA.getNumberOfMinutes());
        assertEquals(5, planJPA.getMusicCollections());
        assertEquals(MusicSuggestions.PERSONALIZED, planJPA.getMusicSuggestions());
    }

    @Test
    public void testApplyPatchWithInvalidNumberOfMinutes() {
        PlanJPA planJPA = mock(PlanJPA.class);

        doThrow(new InconsistencyDataException(PlanJPA.class, "TestPlan", "Invalid number of minutes")).when(planJPA).applyPatch("New Description", "Invalid", "5", "PERSONALIZED");

        try {
            planJPA.applyPatch("New Description", "Invalid", "5", "PERSONALIZED");
        } catch (InconsistencyDataException e) {
            assertEquals("Data from Plan with id TestPlan is inconsistent: Invalid number of minutes", e.getMessage());
        }
    }

}
