package com.example.sisdi_plans.planmanagementapi;

import com.example.sisdi_plans.exceptions.InconsistencyDataException;
import com.example.sisdi_plans.planmanagement.model.MusicSuggestions;
import com.example.sisdi_plans.planmanagement.model.Plan;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlansTest {

    private Plan plan;

    private PlansTest(){
        plan = new Plan("TestPlan", "Description", 100, MusicSuggestions.AUTOMATIC, 10, 10.0f, 100.0f);
    }

    @Test
    public void testPlanConstructor() {
        Plan testPlan = new Plan("TestPlan", "Description", 100, MusicSuggestions.AUTOMATIC, 10, 10.0f, 100.0f);
        assertEquals("TestPlan", testPlan.getName());
        assertEquals("Description", testPlan.getDescription());
        assertEquals(100, testPlan.getNumberOfMinutes());
        assertEquals(MusicSuggestions.AUTOMATIC, testPlan.getMusicSuggestions());
        assertEquals(10, testPlan.getMusicCollections());
        assertEquals(10.0f, testPlan.getMonthlyFee(), 0.01f);
        assertEquals(100.0f, testPlan.getAnnualFee(), 0.01f);
        assertEquals(1, testPlan.getMaxNumberDevices());
        assertTrue(testPlan.getIsActive());
    }

    @Test
    public void testChangeActivityStatus() {
        plan.setActive(true);
        plan.changeActivityStatus();
        assertFalse(plan.getIsActive());
    }

    @Test
    public void testChangeActivityStatusWhenAlreadyInactive() {
        Plan plan = mock(Plan.class);
        when(plan.getName()).thenReturn("TestPlan");
        when(plan.getIsActive()).thenReturn(false);

        doThrow(new InconsistencyDataException(Plan.class, "TestPlan", "Plan is already inactive")).when(plan).changeActivityStatus();

        try {
            plan.changeActivityStatus();
        } catch (InconsistencyDataException e) {
            assertEquals("Data from Plan with id TestPlan is inconsistent: Plan is already inactive", e.getMessage());
        }
    }

    @Test
    public void testApplyPatch() {
        plan.applyPatch("New Description", "50", "5", "PERSONALIZED");
        assertEquals("New Description", plan.getDescription());
        assertEquals(50, plan.getNumberOfMinutes());
        assertEquals(5, plan.getMusicCollections());
        assertEquals(MusicSuggestions.PERSONALIZED, plan.getMusicSuggestions());
    }

    @Test
    public void testApplyPatchWithInvalidNumberOfMinutes() {
        Plan plan = mock(Plan.class);

        doThrow(new InconsistencyDataException(Plan.class, "TestPlan", "Invalid number of minutes")).when(plan).applyPatch("New Description", "Invalid", "5", "PERSONALIZED");

        try {
            plan.applyPatch("New Description", "Invalid", "5", "PERSONALIZED");
        } catch (InconsistencyDataException e) {
            assertEquals("Data from Plan with id TestPlan is inconsistent: Invalid number of minutes", e.getMessage());
        }
    }

}
