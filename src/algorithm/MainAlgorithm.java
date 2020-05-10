package algorithm;

import algorithm.bruteforce.BruteForceAlgorithm;
import datareader.DataReader;
import distance.*;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.*;

public class MainAlgorithm {

    private List<Bus> buses;
    private List<Pupil> pupils;
    private List<School> schools;

    private Map<Pupil, List<PupilDistance>> distanceBetweenPupil;

    private Map<Bus, List<BusDistance>> distanceBetweenBusesAndPupils;

    private Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils;

    private Map<Bus, List<School>> analysisMap;

    private List<Route> minimalRoute = new ArrayList<>();

    private void init(){
        fetchData();
        countDistance();
    }

    private void fetchData() {
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

        analysisMap = DependencyAnalysis.analysis(distanceBetweenBusesAndPupils, distanceBetweenSchoolsAndPupils);
    }

    public void bruteForceAlgorithm() {
        init();
        BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(this);
        minimalRoute = bruteForceAlgorithm.startAlgorithm();
        printMinimalRoute();
    }



    private void printMinimalRoute() {

        for (Route route : minimalRoute) {
            System.out.println("DISTANCE = " + route.getDistance() + " " + route.getBus() +  " " + (route.getSchool()));
            for (Pupil pupil : route.getBusRoute()) {
                System.out.println(pupil);
            }
            System.out.println();
        }
    }

    public Map<Pupil, List<PupilDistance>> getDistanceBetweenPupil() {
        return distanceBetweenPupil;
    }

    public Map<Bus, List<BusDistance>> getDistanceBetweenBusesAndPupils() {
        return distanceBetweenBusesAndPupils;
    }

    public Map<School, List<SchoolDistance>> getDistanceBetweenSchoolsAndPupils() {
        return distanceBetweenSchoolsAndPupils;
    }

    public Map<Bus, List<School>> getAnalysisMap() {
        return analysisMap;
    }
}
