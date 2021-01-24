package io.tblx.routeplanning.solver;

import io.tblx.routeplanning.entities.Route;
import io.tblx.routeplanning.entities.RoutePlan;
import io.tblx.routeplanning.entities.Vehicle;
import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;

import java.time.LocalTime;

class RoutePlanConstraintProviderTest {

    private final ConstraintVerifier<RoutePlanConstraintProvider, RoutePlan> subject =
            ConstraintVerifier.build(new RoutePlanConstraintProvider(), RoutePlan.class, Route.class);

    @Test
    public void whenVehicleIsAssignedToOverlappingRoutes_thenPenalizeHardScore() {
        Vehicle vehicle = new Vehicle("EQC");
        Route route1 = new Route(LocalTime.parse("09:00"), LocalTime.parse("12:00"));
        Route route2 = new Route(LocalTime.parse("10:00"), LocalTime.parse("13:00"));

        route1.setDesignatedVehicle(vehicle);
        route2.setDesignatedVehicle(vehicle);

        subject.verifyThat(RoutePlanConstraintProvider::routesWithSameVehicleMustNotOverlap)
                .given(route1, route2)
                .penalizesBy(2);
    }

    @Test
    public void whenVehicleIsAssignedToNonOverlappingRoutes_thenDontPenalizeHardScore() {
        Vehicle vehicle = new Vehicle("EQC");
        Route route1 = new Route(LocalTime.parse("09:00"), LocalTime.parse("12:00"));
        Route route2 = new Route(LocalTime.parse("13:00"), LocalTime.parse("14:00"));

        route1.setDesignatedVehicle(vehicle);
        route2.setDesignatedVehicle(vehicle);

        subject.verifyThat(RoutePlanConstraintProvider::routesWithSameVehicleMustNotOverlap)
                .given(route1, route2)
                .penalizesBy(0);
    }

    @Test
    public void whenVehicleIsAssignedToRoute_thenPenalizeSoftScore() {
        Vehicle vehicle1 = new Vehicle("EQC");
        Route route1 = new Route(LocalTime.parse("09:00"), LocalTime.parse("12:00"));
        Route route2 = new Route(LocalTime.parse("13:00"), LocalTime.parse("14:00"));

        route1.setDesignatedVehicle(vehicle1);
        route2.setDesignatedVehicle(vehicle1);

        subject.verifyThat(RoutePlanConstraintProvider::useTheLeastAmountOfVehicles)
                .given(route1, route2)
                .penalizesBy(1);
    }
}