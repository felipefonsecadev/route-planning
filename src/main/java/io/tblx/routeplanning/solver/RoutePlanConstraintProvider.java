package io.tblx.routeplanning.solver;

import io.tblx.routeplanning.entities.Route;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import static org.optaplanner.core.api.score.stream.ConstraintCollectors.countDistinct;
import static org.optaplanner.core.api.score.stream.Joiners.equal;
import static org.optaplanner.core.api.score.stream.Joiners.filtering;


public class RoutePlanConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                routesWithSameVehicleMustNotOverlap(constraintFactory),
                useTheLeastAmountOfVehicles(constraintFactory)
        };
    }

    protected Constraint routesWithSameVehicleMustNotOverlap(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Route.class)
            .join(
                Route.class,
                equal(Route::getDesignatedVehicle),
                filtering(Route::isOverlapping)
            )
            .penalize("Routes with same vehicle must not overlap", HardSoftScore.ONE_HARD);
    }

    protected Constraint useTheLeastAmountOfVehicles(ConstraintFactory constraintFactory) {
        return constraintFactory.from(Route.class)
            .groupBy(
                Route::getDesignatedVehicle,
                countDistinct(Route::getDesignatedVehicle)
            )
            .penalize(
                "Use the least amount of vehicles",
                HardSoftScore.ONE_SOFT,
                ((vehicle, count) -> count)
            );
    }
}
