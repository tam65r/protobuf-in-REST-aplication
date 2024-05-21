package com.example.sisdi_subscriptions.subscriptionsmanagementapi;


import com.example.sisdi_subscriptions.subscriptionmanagement.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubscriptionJPATest {

    private SubscriptionJPA subscriptionJPA;

    private SubscriptionJPATest(){
        subscriptionJPA = new SubscriptionJPA("TestSubscriberID", "TestPlan", LocalDateTime.now(), FeeType.MONTHLY, PaymentMethod.CARD, LocalDateTime.now());
    }

    @Test
    public void testSubscriptionConstructor() {
        SubscriptionJPA testSubscriptionJPA = new SubscriptionJPA("TestSubscriberID", "TestPlan", LocalDateTime.now(), FeeType.MONTHLY, PaymentMethod.CARD, LocalDateTime.now());
        assertEquals("TestSubscriberID", testSubscriptionJPA.getSubscriberID());
        assertEquals("TestPlan", testSubscriptionJPA.getPlan());
        assertTrue(testSubscriptionJPA.getStatus());
    }

    @Test
    public void testDeactivateSubscription() {
        subscriptionJPA.deactivate();
        assertFalse(subscriptionJPA.getStatus());
    }

    @Test
    public void testSetRenewed() {
        subscriptionJPA.setRenewed(true);
        assertTrue(subscriptionJPA.getStatus());
    }

    @Test
    public void testSetEndSubscriptionDate() {
        LocalDateTime newEndSubscriptionDate = LocalDateTime.now().plusDays(30);
        subscriptionJPA.setEndSubscriptionDate(newEndSubscriptionDate);
        assertEquals(newEndSubscriptionDate, subscriptionJPA.getEndSubscriptionDate());
    }

    @Test
    public void testSetSubscriberID() {
        subscriptionJPA.setSubscriberID("NewSubscriberID");
        assertEquals("NewSubscriberID", subscriptionJPA.getSubscriberID());
    }

    @Test
    public void testSetPlan() {
        subscriptionJPA.setPlan("NewPlan");
        assertEquals("NewPlan", subscriptionJPA.getPlan());
    }

    @Test
    public void testDeactivateAlreadyDeactivatedSubscription() {
        SubscriptionJPA subscriptionJPA = mock(SubscriptionJPA.class);
        when(subscriptionJPA.getStatus()).thenReturn(false);

        doThrow(new IllegalStateException("Subscription is already deactivated")).when(subscriptionJPA).deactivate();

        try {
            subscriptionJPA.deactivate();
        } catch (IllegalStateException e) {
            assertEquals("Subscription is already deactivated", e.getMessage());
        }
    }
}
