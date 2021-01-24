package io.tblx.routeplanning.entities;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class RoutePlan {

    @ValueRangeProvider(id = "vehicles")
    @ProblemFactCollectionProperty
    private List<Vehicle> vehicles;

    @PlanningEntityCollectionProperty
    private List<Route> routes;

    @PlanningScore
    private HardSoftScore score;

    private RoutePlan() {
    }

    public RoutePlan(List<Vehicle> vehicles, List<Route> routes) {
        this.vehicles = vehicles;
        this.routes = routes;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public HardSoftScore getScore() {
        return score;
    }
}
