package algorithm;

import java.util.List;

public class Route {

    private int distance;

    private List<Integer> busRoute;

    private int schoolId;

    public Route() {
        this.distance = Integer.MAX_VALUE;
    }

    public Route(int distance, List<Integer> busRoute, int schoolId) {
        this.distance = distance;
        this.busRoute = busRoute;
        this.schoolId = schoolId;

    }

    public List<Integer> getBusRoute() {
        return busRoute;
    }

    public void setRoute(List<Integer> busRoute) {
        this.busRoute = busRoute;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
