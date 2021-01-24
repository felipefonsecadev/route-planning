package io.tblx.routeplanning;

import io.tblx.routeplanning.entities.Route;
import io.tblx.routeplanning.entities.RoutePlan;
import io.tblx.routeplanning.entities.Vehicle;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RoutePlanner {

    public static void main(String[] args) {
        Random random = new Random();

        SolverFactory<RoutePlan> solverFactory = SolverFactory.createFromXmlResource(
            "routePlanningSolverConfig.xml"
        );
        Solver<RoutePlan> solver = solverFactory.buildSolver();

        List<Vehicle> vehicles = new ArrayList<>();
        List<Route> routes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            vehicles.add(new Vehicle("Vehicle " + i));
        }

        for (int i = 0; i < 6; i++) {
            LocalTime startTime = LocalTime.of(8, 0);
            LocalTime endTime = startTime.plusHours(1);
            routes.add(new Route(startTime, endTime));
        }

        routes.sort(Comparator.comparing(Route::getStartTime));

        RoutePlan unsolvedRoutePlan = new RoutePlan(vehicles, routes);
        RoutePlan solvedRoutePlan = solver.solve(unsolvedRoutePlan);

        System.out.println("Solved route plan with score " + solvedRoutePlan.getScore().getSoftScore());
        System.out.println("Solution is feasible " + solvedRoutePlan.getScore().isFeasible());

        for (Route route : solvedRoutePlan.getRoutes()) {
            System.out.println(route);
        }
    }
}
