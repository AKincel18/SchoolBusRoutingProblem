package algorithm.nearestneighbour;

import algorithm.MainAlgorithm;
import algorithm.Route;
import distance.BusDistance;
import distance.PupilDistance;
import distance.SchoolDistance;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.*;

public class NearestNeighbourAlgorithm extends MainAlgorithm{

    private List<Route> minimalRoute = new ArrayList<>();

    @Override
    public void routeBusToSchool(Map.Entry<Bus, List<School>> map) {
        Route minRoute = new Route();
        for (School school : map.getValue()) {
            List<Pupil> listPupil = getPupilFromTheSameSchool(school);
            Route route = getMinimalRouteForSchool(listPupil, map.getKey(), school);
            route.setBus(map.getKey());
            minRoute = (minRoute.getDistance() > route.getDistance() ? route : minRoute);
        }
        removeVisitedSchool(minRoute.getSchool());
        minimalRoute.add(minRoute);
    }

    private Route getMinimalRouteForSchool(List<Pupil> listPupil, Bus bus, School school) {

        Map.Entry<School, List<SchoolDistance>> schoolDistance = getSchoolDistanceMap(school);
        List<BusDistance> busDistance = getBusDistance(bus);
        assert schoolDistance != null;
        return findRoute(listPupil, schoolDistance, busDistance);

    }


    private Route findRoute(List<Pupil> listPupil, Map.Entry<School, List<SchoolDistance>> schoolDistance, List<BusDistance> busDistance) {

        PupilDistance pupilToBus = getNearestPupilToBus(busDistance, schoolDistance.getKey().getId());
        Pupil currentPupil = pupilToBus.getPupil();
        Integer distance = pupilToBus.getDistance();

        List<Pupil> pupilRoute = new ArrayList<>();
        pupilRoute.add(currentPupil);

        listPupil.remove(currentPupil);

        if (listPupil.size() == 0)
            distance+=getDistanceBetweenPupilAndBus(currentPupil, schoolDistance.getValue());

        while (listPupil.size() > 0) {

            PupilDistance pupilDistance = findNearestPupil(currentPupil, listPupil);
            if (pupilDistance != null) {
                currentPupil = pupilDistance.getPupil();
                distance += pupilDistance.getDistance();
            }

            if (listPupil.size() == 1) {
                currentPupil = listPupil.get(0);
                distance+= getDistanceBetweenPupilAndBus(currentPupil, schoolDistance.getValue());
            }

            pupilRoute.add(currentPupil);
            listPupil.remove(currentPupil);
        }

        return new Route(distance, pupilRoute, schoolDistance.getKey());
    }

    private Integer getDistanceBetweenPupilAndBus(Pupil pupil, List<SchoolDistance> schoolDistances) {
        for (SchoolDistance schoolDistance : schoolDistances) {
            if (schoolDistance.getPupil().equals(pupil)) {
                return schoolDistance.getDistance();
            }
        }
        return 0;
    }

    private PupilDistance findNearestPupil(Pupil currentPupil, List<Pupil> pupils) {
        List<PupilDistance> pupilList = getDistanceBetweenPupil().get(currentPupil);
        pupilList.sort(Comparator.comparing(PupilDistance::getDistance));
        for (PupilDistance pupilDistance : pupilList) {
            if (pupils.contains(pupilDistance.getPupil())) {
                return pupilDistance;
            }
        }
        return null;

    }


    private PupilDistance getNearestPupilToBus(List<BusDistance> busDistances, int schoolId) {
        Integer minDistance = Integer.MAX_VALUE;
        Pupil pupil = null;
        for (BusDistance busDistance : busDistances) {
            if (busDistance.getPupil().getSchoolId() == schoolId) {
                if (minDistance > busDistance.getDistance()) {
                    minDistance = busDistance.getDistance();
                    pupil = busDistance.getPupil();
                }
            }
        }
        return new PupilDistance(pupil, minDistance);


    }

    @Override
    public List<Route> getMinimalRoute() {
        return minimalRoute;
    }
}
