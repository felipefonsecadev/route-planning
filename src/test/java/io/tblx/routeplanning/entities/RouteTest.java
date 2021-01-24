package io.tblx.routeplanning.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RouteTest {

    @Test
    public void whenRouteStartTimeIsAfterAnotherRouteStartTimeButBeforeStopTime_thenReturnTrue() {
        Route route1 = new Route(LocalTime.parse("09:00:00"), LocalTime.parse("12:00:00"));
        Route route2 = new Route(LocalTime.parse("10:00:00"), LocalTime.parse("13:00:00"));

        assertTrue(route1.isOverlapping(route2));
    }

    @Test
    public void whenRouteStartTimeIsAfterAnotherRouteEndTime_thenReturnFalse() {
        Route route1 = new Route(LocalTime.parse("09:00:00"), LocalTime.parse("12:00:00"));
        Route route2 = new Route(LocalTime.parse("13:00:00"), LocalTime.parse("14:00:00"));

        assertFalse(route1.isOverlapping(route2));
    }

    @Test
    public void whenSessionStartTimeIsEqualToAnotherSessionEndTime_thenReturnTrue() {
        Route route1 = new Route(LocalTime.parse("09:00:00"), LocalTime.parse("12:00:00"));
        Route route2 = new Route(LocalTime.parse("12:00:00"), LocalTime.parse("13:00:00"));

        assertFalse(route1.isOverlapping(route2));
    }
}