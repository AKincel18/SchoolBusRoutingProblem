package algorithm.bruteforce;

import algorithm.MainAlgorithm;
import algorithm.Route;
import distance.BusDistance;
import distance.PupilDistance;
import distance.SchoolDistance;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BruteForceAlgorithm extends MainAlgorithm{

    private List<Route> minimalRoute = new ArrayList<>();

    @Override
    public void routeBusToSchool(Map.Entry<Bus, List<School>> busMap) {
        Route minRoute = new Route();
        for (School school : busMap.getValue()) {
            List<Pupil> listPupil = getPupilFromTheSameSchool(school);
            Permutation permutation = new Permutation(listPupil, listPupil.size());
            Route route = getMinimalRouteForSchool(permutation.getPermutation(), busMap.getKey(), school);
            route.setBus(busMap.getKey());
            minRoute = (minRoute.getDistance() > route.getDistance() ? route : minRoute);
        }

        removeVisitedSchool(minRoute.getSchool());
        minimalRoute.add(minRoute);
    }


    private Route getMinimalRouteForSchool(List<List<Pupil>> routes, Bus bus,School school) {
        int minDistance = Integer.MAX_VALUE;
        List<Pupil> minRoute = null;

        Map.Entry<School, List<SchoolDistance>> schoolDistance = getSchoolDistanceMap(school);
        List<BusDistance> busDistance = getBusDistance(bus);
        for (List<Pupil> route : routes) {
            int tempDistance = countDistanceInRoute(route, busDistance, schoolDistance);
            if (minDistance > tempDistance) {
                minDistance = tempDistance;
                minRoute = route;
            }
        }
        return new Route(minDistance, minRoute, schoolDistance.getKey());

    }


    private int countDistanceInRoute(List<Pupil> route, List<BusDistance> busDistance, Map.Entry<School, List<SchoolDistance>> schoolDistance) {
        int distanceBetweenPupils = countDistanceBetweenPupils(route);
        int distanceBetweenBusAndPupil = countDistanceBetweenBusAndPupil(route.get(0), busDistance);
        int distanceBetweenPupilAndSchool = countDistanceBetweenSchoolAndPupil(route.get(route.size() - 1), schoolDistance.getValue());

        return  distanceBetweenPupils + distanceBetweenBusAndPupil + distanceBetweenPupilAndSchool;
    }

    private int countDistanceBetweenSchoolAndPupil(Pupil pupil, List<SchoolDistance> schoolDistances) {
        for (SchoolDistance schoolDistance : schoolDistances) {
            if (schoolDistance.getPupil().equals(pupil))
                return schoolDistance.getDistance();
        }
        return 0;
    }

    private int countDistanceBetweenBusAndPupil(Pupil pupil, List<BusDistance> busDistances) {
        for (BusDistance busDistance : busDistances) {
            if (busDistance.getPupil().equals(pupil))
                return busDistance.getDistance();
        }
        return 0;
    }


    private int countDistanceBetweenPupils(List<Pupil> route) {

        int distance = 0;
        for(int i = 0; i < route.size() - 1; i++) {
            distance+=countDistanceBetweenTwoPupils(route.get(i), route.get(i + 1));
        }
        return distance;
    }

    private int countDistanceBetweenTwoPupils(Pupil pupil, Pupil pupil2) {
        for (Map.Entry<Pupil, List<PupilDistance>> map : getDistanceBetweenPupil().entrySet()) {
            if (map.getKey().equals(pupil)) {
                for (PupilDistance pupilDistance : map.getValue()) {
                    if (pupilDistance.getPupil().equals(pupil2)) {
                        return pupilDistance.getDistance();
                    }
                }
            }
        }
        return 0;
    }


    @Override
    protected List<Route> getMinimalRoute() {
        return minimalRoute;
    }
}
