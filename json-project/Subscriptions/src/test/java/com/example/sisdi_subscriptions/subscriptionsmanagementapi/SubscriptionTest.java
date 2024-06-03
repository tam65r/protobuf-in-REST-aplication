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
public class SubscriptionTest {

    private Subscription subscription;

    private SubscriptionTest(){
        subscription = new Subscription("TestSubscriberID", "TestPlan", LocalDateTime.now(), FeeType.MONTHLY, PaymentMethod.CARD, LocalDateTime.now());
    }

    @Test
    public void testSubscriptionConstructor() {
        Subscription testSubscription = new Subscription("TestSubscriberID", "TestPlan", LocalDateTime.now(), FeeType.MONTHLY, PaymentMethod.CARD, LocalDateTime.now());
        assertEquals("TestSubscriberID", testSubscription.getSubscriberID());
        assertEquals("TestPlan", testSubscription.getPlan());
        assertTrue(testSubscription.getStatus());
    }

    @Test
    public void testDeactivateSubscription() {
        subscription.deactivate();
        assertFalse(subscription.getStatus());
    }

    @Test
    public void testSetRenewed() {
        subscription.setRenewed(true);
        assertTrue(subscription.getStatus());
    }

    @Test
    public void testSetEndSubscriptionDate() {
        LocalDateTime newEndSubscriptionDate = LocalDateTime.now().plusDays(30);
        subscription.setEndSubscriptionDate(newEndSubscriptionDate);
        assertEquals(newEndSubscriptionDate, subscription.getEndSubscriptionDate());
    }

    @Test
    public void testSetSubscriberID() {
        subscription.setSubscriberID("NewSubscriberID");
        assertEquals("NewSubscriberID", subscription.getSubscriberID());
    }

    @Test
    public void testSetPlan() {
        subscription.setPlan("NewPlan");
        assertEquals("NewPlan", subscription.getPlan());
    }

    @Test
    public void testDeactivateAlreadyDeactivatedSubscription() {
        Subscription subscription = mock(Subscription.class);
        when(subscription.getStatus()).thenReturn(false);

        doThrow(new IllegalStateException("Subscription is already deactivated")).when(subscription).deactivate();

        try {
            subscription.deactivate();
        } catch (IllegalStateException e) {
            assertEquals("Subscription is already deactivated", e.getMessage());
        }
    }
}
