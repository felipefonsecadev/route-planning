package io.tblx.routeplanning.entities;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalTime;
import java.util.UUID;

@PlanningEntity
public class Route {

    private UUID id;
    private LocalTime startTime;
    private LocalTime endTime;

    @PlanningVariable(valueRangeProviderRefs = "vehicles")
    private Vehicle designatedVehicle;

    private Route() {
    }

    public Route(LocalTime startTime, LocalTime endTime) {
        this.id = UUID.randomUUID();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", designatedVehicle=" + designatedVehicle +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Vehicle getDesignatedVehicle() {
        return designatedVehicle;
    }

    public void setDesignatedVehicle(Vehicle designatedVehicle) {
        this.designatedVehicle = designatedVehicle;
    }

    public boolean isOverlapping(Route other) {
        return !this.equals(other) &&
                startTime.isBefore(other.getEndTime()) &&
                other.getStartTime().isBefore(endTime);
    }
}
