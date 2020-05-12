package algorithm;

import datareader.DataReader;
import distance.*;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.*;

public abstract class MainAlgorithm extends DependencyAnalysis {

    private List<Bus> buses;
    private List<Pupil> pupils;
    private List<School> schools;

    private Map<Pupil, List<PupilDistance>> distanceBetweenPupil;

    private Map<Bus, List<BusDistance>> distanceBetweenBusesAndPupils;

    private Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils;

    private Map<Bus, List<School>> analysisMap;

    private List<Route> minimalRoute = new ArrayList<>();

    protected abstract void routeBusToSchool(Map.Entry<Bus, List<School>> map);

    protected abstract List<Route> getMinimalRoute();

    protected MainAlgorithm() {
        init();
    }

    private void init(){
        fetchData();
        countDistance();
    }

    private void fetchData() {
        DataReader dataReader = new DataReader();

        buses = dataReader.getBuses();
        pupils = dataReader.getPupils();
        schools = dataReader.getSchools();

/*        Bus.printBuses(buses);
        Pupil.printPupils(pupils);
        School.printSchools(schools);*/
    }

    private void countDistance () {
        CountDistance distance = new CountDistance(schools, pupils, buses);

        distanceBetweenPupil = distance.getDistanceBetweenPupils();
        distanceBetweenBusesAndPupils = distance.getDistanceBetweenBusesAndPupils();
        distanceBetweenSchoolsAndPupils = distance.getDistanceBetweenSchoolsAndPupils();

/*        distance.printDistances();
        distance.printBusDistance();
        distance.printSchoolDistance();*/

        analysisMap = analysis(distanceBetweenBusesAndPupils, distanceBetweenSchoolsAndPupils);
    }

    public void startAlgorithm() {
        for (Map.Entry<Bus, List<School>> map : analysisMap.entrySet()) {
            routeBusToSchool(map);
        }
        minimalRoute = getMinimalRoute();
        printMinimalRoute();
    }

    protected void removeVisitedSchool(School schoolToRemove) {
        for ( Map.Entry<Bus, List<School>> map : analysisMap.entrySet()) {
            map.getValue().removeIf(schoolToRemove::equals);
        }
    }

    protected Map.Entry<School, List<SchoolDistance>> getSchoolDistanceMap(School school) {
        for (Map.Entry<School, List<SchoolDistance>> map : distanceBetweenSchoolsAndPupils.entrySet()) {
            if (map.getKey().equals(school)) {
                return map;
            }
        }
        return null;
    }

    protected List<BusDistance> getBusDistance(Bus bus) {
        for (Map.Entry<Bus, List<BusDistance>> map : distanceBetweenBusesAndPupils.entrySet()) {
            if (map.getKey().equals(bus)) {
                return map.getValue();
            }
        }
        return null;
    }


    protected List<Pupil> getPupilFromTheSameSchool(School school) {

        List<SchoolDistance> schoolDistancesList = getSchoolDistance(school);

        List<Pupil> pupils = new ArrayList<>();
        for (SchoolDistance schoolDistance : schoolDistancesList) {
            pupils.add(schoolDistance.getPupil());
        }
        return pupils;
    }

    private List<SchoolDistance> getSchoolDistance(School school) {
        List<SchoolDistance> schoolDistancesList = null;
        for (Map.Entry<School, List<SchoolDistance>> map : distanceBetweenSchoolsAndPupils.entrySet()) {
            if (map.getKey().equals(school)) {
                schoolDistancesList = map.getValue();
            }
        }
        return schoolDistancesList;
    }

    private void printMinimalRoute() {

        for (Route route : minimalRoute) {
            System.out.println("DISTANCE = " + route.getDistance());
            System.out.println(route.getBus());
            for (Pupil pupil : route.getBusRoute()) {
                System.out.println(pupil);
            }
            System.out.println(route.getSchool());
            System.out.println();
        }
    }

    private int countSumOfDistance() {
        int sum =0;
        for (Route route : minimalRoute) {
            sum+=route.getDistance();
        }
        return sum;
    }

    public void printSumOfDistance() {
        System.out.println("Sum of the distance = " + countSumOfDistance());
    }

    protected Map<Pupil, List<PupilDistance>> getDistanceBetweenPupil() {
        return distanceBetweenPupil;
    }
}
