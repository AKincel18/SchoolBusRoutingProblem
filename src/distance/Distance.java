package distance;

import model.Bus;
import model.Coords;
import model.Pupil;
import model.School;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Distance {

    private List<List<Integer>> distanceBetweenPupils = new ArrayList<>();

    private List<List<Integer>> distanceBetweenBusesAndPupils = new ArrayList<>();

    private List<List<Integer>> distanceBetweenSchoolsAndPupils = new ArrayList<>();

    private List<School> schools;

    private List<Pupil> pupils;

    private List<Bus> buses;

    public Distance(List<School> schools, List<Pupil> pupils, List<Bus> buses) {
        this.schools = schools;
        this.pupils = pupils;
        this.buses = buses;
        distanceAll();
    }

    private void distanceAll(){

        distanceBetweenPupils();
        distanceBetweenBusesAndPupils();
        distanceBetweenSchoolsAndPupils();
    }

    private void distanceBetweenPupils() {

        for (Pupil pupil : pupils) {
            distanceBetweenPupils.add(countDistanceForOnePupilToOthers(pupil));
        }
    }

    private void distanceBetweenBusesAndPupils() {

        for (Bus bus : buses) {
            distanceBetweenBusesAndPupils.add(countDistanceBusPupils(bus));
        }
    }

    private void distanceBetweenSchoolsAndPupils() {

        for (School school : schools) {
            distanceBetweenSchoolsAndPupils.add(countDistanceSchoolPupils(school));
        }
    }

    private List<Integer> countDistanceSchoolPupils(School school){

        List<Integer> distance = new ArrayList<>();

        for (Pupil pupil : pupils) {
            if (pupil.getSchoolId() == school.getId()) {
                distance.add(countDistanceBetweenCoords(pupil.getCoords(), school.getCoords()));
            }
            else {
                distance.add(-1);
            }
        }

        return distance;
    }

    private List<Integer> countDistanceBusPupils(Bus bus) {

        List<Integer> distance = new ArrayList<>();
        for (Pupil pupil : pupils) {
            distance.add(countDistanceBetweenCoords(pupil.getCoords(), bus.getCoords()));
        }
        return distance;
    }


    private List<Integer> countDistanceForOnePupilToOthers(Pupil pupil) {
        List<Integer> distance = new ArrayList<>();

        School pupilSchool = findSchool(pupil);

        for (Pupil p : pupils) {
            if (pupil.getCoords() != p.getCoords()){
                if (pupilSchool.getId() == p.getSchoolId()) {
                    distance.add(countDistanceBetweenCoords(pupil.getCoords(), p.getCoords()));
                }
                else {
                    distance.add(-1);
                }
            }
            else {
                distance.add(- 1);
            }
        }

        return distance;


    }

    private School findSchool(Pupil pupil) {
        for (School school : schools) {
            if (pupil.getSchoolId() == school.getId())
                return school;
        }
        return new School();
    }

    private Integer countDistanceBetweenCoords(Coords c1, Coords c2) {
        return new Double(Math.sqrt(Math.pow((double) c2.getX() - c1.getX(), 2.0) +
                Math.pow((double) c2.getY() - c1.getY(), 2.0))).intValue();
    }

    public List<List<Integer>> getDistanceBetweenPupils() {
        return distanceBetweenPupils;
    }

    public List<List<Integer>> getDistanceBetweenBusesAndPupils() {
        return distanceBetweenBusesAndPupils;
    }

    public List<List<Integer>> getDistanceBetweenSchoolsAndPupils() {
        return distanceBetweenSchoolsAndPupils;
    }

    public void printDistances() {

        int nrPupil = 0;

        for (List<Integer> list : distanceBetweenPupils) {
            nrPupil++;
            System.out.println("Pupil:" + nrPupil);

            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println();

        }
    }

    public void printBusDistance() {
        int nrBus = 0;

        for (List<Integer> list : distanceBetweenBusesAndPupils) {
            nrBus++;
            System.out.println("Bus nr: " + nrBus);

            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println();

        }
    }

    public void printSchoolDistance() {
        int nrSchool = 0;

        for (List<Integer> list : distanceBetweenSchoolsAndPupils) {
            nrSchool++;
            System.out.println("School nr: " + nrSchool);

            for (Integer integer : list) {
                System.out.print(integer + " ");
            }
            System.out.println();

        }
    }
}
