package algorithm;

import model.Bus;
import model.Pupil;
import model.School;

import java.util.List;

public class Route {

    private int distance;

    private List<Pupil> busRoute;

    private School school;

    private Bus bus;

    public Route() {
        this.distance = Integer.MAX_VALUE;
    }

    public Route(int distance, List<Pupil> busRoute, School school) {
        this.distance = distance;
        this.busRoute = busRoute;
        this.school = school;

    }

    public List<Pupil> getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(List<Pupil> busRoute) {
        this.busRoute = busRoute;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}
