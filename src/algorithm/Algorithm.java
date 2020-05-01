package algorithm;

import datareader.DataReader;
import distance.Distance;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Algorithm {

    private List<Bus> buses;
    private List<Pupil> pupils;
    private List<School> schools;

    private List<List<Integer>> distanceBetweenPupil;

    private List<List<Integer>> distanceBetweenBusesAndPupils;

    private List<List<Integer>> distanceBetweenSchoolsAndPupils;

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
        Distance distance = new Distance(schools, pupils, buses);

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
            for (List<Integer> busDistance : distanceBetweenBusesAndPupils) {
                routeBusToSchool(busDistance);
            }
            //removeElements();
        //}

    }

/*    private void removeElements() {
        Route minRoute = new Route();
        int pos = 0;
        for (int i = 0; i < minimalRoute.size(); i++) {
            if (minRoute.getDistance() > minimalRoute.get(i).getDistance()){
                minRoute = minimalRoute.get(i);
                pos = i;
            }
        }

        removePupils(minRoute);
        removeBuses(minRoute);
        removeSchools(pos);
        removeRoute(minRoute);
    }

    private void removeRoute(Route minRoute) {
        minimalRoute.clear();
        minimalRoute.add(minRoute);
    }

    private void removeSchools(int pos) {
        distanceBetweenSchoolsAndPupils.remove(pos);
    }

    private void removeBuses(Route route) {
        distanceBetweenBusesAndPupils.remove(route.getSchoolId());
    }

    private void removePupils(Route route) {

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




    private void routeBusToSchool(List<Integer> busDistance) {

        Route minRoute = new Route();
        int schoolId = 0;
        for (List<Integer> schoolDistance : distanceBetweenSchoolsAndPupils) {
            //b -> u ... u -> s
            List<Integer> listPupil = getPupil(schoolDistance);
            Permutation permutation = new Permutation(listPupil, listPupil.size(), listPupil.size());
            List<List<Integer>> permutations = permutation.getPermutation();
            Route route = getMinimalRouteForSchool(permutations, busDistance, schoolDistance, schoolId);
            minRoute = (minRoute.getDistance() > route.getDistance() ? route : minRoute);
            schoolId++;
        }
        minimalRoute.add(minRoute);
    }

    private Route getMinimalRouteForSchool(List<List<Integer>> routes, List<Integer> busDistance, List<Integer> schoolDistance, int schoolId) {
        int minDistance = Integer.MAX_VALUE;
        List<Integer> minRoute = null;

        for (List<Integer> route : routes) {
            int tempDistance = countDistanceInRoute(route, busDistance, schoolDistance);
            if (minDistance > tempDistance) {
                minDistance = tempDistance;
                minRoute = route;
            }
        }
        return new Route(minDistance, minRoute, schoolId);

    }

    private int countDistanceInRoute(List<Integer> route, List<Integer> busDistance, List<Integer> schoolDistance) {
        int distanceBetweenPupils = countDistanceBetweenPupils(route);
        int distanceBetweenBusAndPupil = busDistance.get(route.get(0));
        int distanceBetweenPupilAndSchool = schoolDistance.get(route.get(route.size() - 1));

        return  distanceBetweenPupils + distanceBetweenBusAndPupil + distanceBetweenPupilAndSchool;
    }


    private int countDistanceBetweenPupils(List<Integer> route) {

        int distance = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int pos = i + 1;
            distance+= distanceBetweenPupil.get(route.get(i)).get(route.get(pos));
        }
        return distance;
    }



    private List<Integer> getPupil(List<Integer> schoolDistance) {
        List<Integer> listPupil = new ArrayList<>();
        int pos = 0;
        for ( Integer i : schoolDistance) {
            if (i != -1) {
                listPupil.add(pos);
            }
            pos++;
        }
        return listPupil;
    }

    private void printMinimalRoute() {
        int pos = 0;

        for (Route route : minimalRoute) {
            pos ++;
            System.out.println("BUS NR = " + pos + "; distance = " + route.getDistance() + " school nr = " + (route.getSchoolId() + 1));
            for (Integer pupil : route.getBusRoute()) {
                System.out.println(pupil + 1);
            }
        }
    }
}
