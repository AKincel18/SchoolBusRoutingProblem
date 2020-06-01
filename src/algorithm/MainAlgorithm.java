package algorithm;

import algorithm.bruteforce.BruteForceAlgorithm;
import algorithm.nearestneighbour.NearestNeighbourAlgorithm;
import datareader.DataReader;
import distance.*;
import model.Bus;
import model.Pupil;
import model.School;
import others.Constants;
import others.WrongDataException;

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

    private double algorithmTime;

    private boolean isDataCorrect = true;

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

    private void checkDataCorrectness() throws WrongDataException {
        if (buses.size() != schools.size()) {
            throw new WrongDataException(Constants.exception1);
        } else if (isEmptyBus()) {
            throw new WrongDataException(Constants.exception2);
        }
    }

    private boolean isEmptyBus(){
        for (Map.Entry<Bus, List<School>> map : analysisMap.entrySet()) {
            if (map.getValue().size() == 0)
                return true;
        }
        return false;
    }


    public void startAlgorithm() {
        try {
            checkDataCorrectness();
            printAlgorithmName();

            long start = System.nanoTime();
            for (Map.Entry<Bus, List<School>> map : analysisMap.entrySet()) {
                routeBusToSchool(map);
            }
            long elapsedTime = System.nanoTime() - start;
            algorithmTime = (double)elapsedTime / 1_000_000_000.0;

            minimalRoute = getMinimalRoute();
            printMinimalRoute();
        } catch (WrongDataException e) {
            isDataCorrect = false;
            System.out.println(e.getMessage());
        }

    }

    protected void removeVisitedSchool(School schoolToRemove) throws WrongDataException {
        for ( Map.Entry<Bus, List<School>> map : analysisMap.entrySet()) {
            try {
                map.getValue().removeIf(schoolToRemove::equals);
            } catch (NullPointerException e) {
                isDataCorrect = false;
                throw new WrongDataException(Constants.exception2);
            }
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
        if (isDataCorrect) {
            int i = 1;
            for (Route route : minimalRoute) {
                System.out.println(Constants.pathNumber + i);
                System.out.println(route.getBus());
                for (Pupil pupil : route.getBusRoute()) {
                    System.out.println(pupil);
                }
                System.out.println(route.getSchool());
                System.out.println(Constants.distance + route.getDistance());
                System.out.println();
                i++;
            }
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
        if (isDataCorrect) {
            if (this instanceof BruteForceAlgorithm) {
                System.out.println(Constants.extraSpaces);
                System.out.println(Constants.summary);
                System.out.println(Constants.extraSpaces);
                System.out.println();
                System.out.println(Constants.sumOfTheDistance);
                System.out.println(Constants.bruteForceAlgorithm + countSumOfDistance());
            } else if (this instanceof NearestNeighbourAlgorithm) {
                System.out.println(Constants.nearestNeighbourAlgorithm + countSumOfDistance());
            }
        }
    }

    protected Map<Pupil, List<PupilDistance>> getDistanceBetweenPupil() {
        return distanceBetweenPupil;
    }

    public void printAlgorithmTime() {
        if (isDataCorrect) {
            if (this instanceof BruteForceAlgorithm) {
                System.out.println();
                System.out.println(Constants.time);
                System.out.println(Constants.bruteForceAlgorithm + algorithmTime);
            } else if (this instanceof NearestNeighbourAlgorithm) {
                System.out.println(Constants.nearestNeighbourAlgorithm + algorithmTime);
            }
        }
    }

    private void printAlgorithmName() {

        System.out.println();
        System.out.println(Constants.extraSpaces);
        if (this instanceof BruteForceAlgorithm) {
            System.out.println(Constants.bruteForceAlgorithm);
        } else if (this instanceof NearestNeighbourAlgorithm) {
            System.out.println(Constants.nearestNeighbourAlgorithm);
        }
        System.out.println(Constants.extraSpaces);
        System.out.println();
    }
}
