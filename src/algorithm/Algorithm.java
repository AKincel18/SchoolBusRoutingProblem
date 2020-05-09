package algorithm;

import datareader.DataReader;
import distance.*;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Algorithm {

    private List<Bus> buses;
    private List<Pupil> pupils;
    private List<School> schools;

    private Map<Pupil, List<PupilDistance>> distanceBetweenPupil;

    private Map<Bus, List<BusDistance>> distanceBetweenBusesAndPupils;

    private Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils;

    private List<Route> minimalRoute = new ArrayList<>();



    public void init () {
        getData();
        countDistance();
    }

    private void getData() {
        DataReader dataReader = new DataReader();

        buses = dataReader.getBuses();
        pupils = dataReader.getPupils();
        schools = dataReader.getSchools();

        Bus.printBuses(buses);
        Pupil.printPupils(pupils);
        School.printSchools(schools);
    }

    private void countDistance () {
        CountDistance distance = new CountDistance(schools, pupils, buses);

        distanceBetweenPupil = distance.getDistanceBetweenPupils();
        distanceBetweenBusesAndPupils = distance.getDistanceBetweenBusesAndPupils();
        distanceBetweenSchoolsAndPupils = distance.getDistanceBetweenSchoolsAndPupils();

        distance.printDistances();
        distance.printBusDistance();
        distance.printSchoolDistance();

        busToSchool();
        printMinimalRoute();
    }

    private void busToSchool(){

        //while (distanceBetweenBusesAndPupils.size() > 0) {
            for (Map.Entry<Bus, List<BusDistance>> entry: distanceBetweenBusesAndPupils.entrySet()) {
                routeBusToSchool(entry.getValue());
            }
            //removeElements();
        //}

    }

    private void removeElements() {
        Route minRoute = new Route();
        int pos = 0;
        for (int i = 0; i < minimalRoute.size(); i++) {
            if (minRoute.getDistance() > minimalRoute.get(i).getDistance()){
                minRoute = minimalRoute.get(i);
                pos = i;
            }
        }

        //removePupils(minRoute);
        //removeBuses(minRoute);
        //removeSchools(pos);
        //removeRoute(minRoute);
    }

    private void removeRoute(Route minRoute) {
        minimalRoute.clear();
        minimalRoute.add(minRoute);
    }

    private void removeSchools(int pos) {
        distanceBetweenSchoolsAndPupils.remove(pos);
    }

/*    private void removeBuses(Route route) {
        distanceBetweenBusesAndPupils.remove(route.getSchoolId());
        for (List<Integer> busDistance : distanceBetweenBusesAndPupils) {
            for ()
        }
    }*/

/*    private void removePupils(Route route) {

        Iterator<List<Integer>> i = distanceBetweenPupil.iterator();
        int pos = 0;
        while (i.hasNext()) {
            i.next();
            for (int pupil : route.getBusRoute()) {
                if (pos == pupil) {
                    i.remove();
                }
            }
            pos++;
        }
    }*/




    private void routeBusToSchool(List<BusDistance> busDistance) {

        Route minRoute = new Route();
        for (Map.Entry<School, List<SchoolDistance>> map : distanceBetweenSchoolsAndPupils.entrySet()) {
            //b -> u ... u -> s
            List<Pupil> listPupil = getPupilFromTheSameSchool(map.getValue());
            Permutation permutation = new Permutation(listPupil, listPupil.size());
            List<List<Pupil>> permutations = permutation.getPermutation();
            Route route = getMinimalRouteForSchool(permutations, busDistance, map);
            minRoute = (minRoute.getDistance() > route.getDistance() ? route : minRoute);
        }
        minimalRoute.add(minRoute);
    }

    private List<Pupil> getPupilFromTheSameSchool(List<SchoolDistance> value) {
        List<Pupil> pupils = new ArrayList<>();
        for (SchoolDistance schoolDistance : value) {
            pupils.add(schoolDistance.getPupil());
        }
        return pupils;
    }

    private Route getMinimalRouteForSchool(List<List<Pupil>> routes, List<BusDistance> busDistance,Map.Entry<School, List<SchoolDistance>> schoolDistance) {
        int minDistance = Integer.MAX_VALUE;
        List<Pupil> minRoute = null;

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
        for (Map.Entry<Pupil, List<PupilDistance>> map : distanceBetweenPupil.entrySet()) {
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

/*    private List<List<PupilDistance>> getPupilKey(List<Pupil> pupils) {
        List<List<PupilDistance>> distances = new ArrayList<>();

        for (Map.Entry<Pupil, List<PupilDistance>> map : distanceBetweenPupil.entrySet()) {
            for (Pupil pupil : pupils) {
                if (map.getKey().equals(pupil)) {
                    distances.add(map.getValue());
                }
            }
        }
        return distances;
    }*/



/*    private List<Pupil> getPupil(Pupil schoolDistance) {
        List<Integer> listPupil = new ArrayList<>();
        int pos = 0;
        for ( Integer i : schoolDistance) {
            if (i != -1) {
                listPupil.add(pos);
            }
            pos++;
        }
        return listPupil;
    }*/

    private void printMinimalRoute() {
        int pos = 0;

        for (Route route : minimalRoute) {
            System.out.print("BUS NR = " + pos + " distance = " + route.getDistance() + " school nr = " + (route.getSchool()) + " pupil nr: ");
            for (Pupil pupil : route.getBusRoute()) {
                System.out.print(pupil + ", ");
            }
            pos ++;
            System.out.println();
        }
    }
}
