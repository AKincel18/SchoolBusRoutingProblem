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

    private List<School> schools;

    private List<Pupil> pupils;

    private List<Bus> buses;

    public Distance(List<School> schools, List<Pupil> pupils, List<Bus> buses) {
        this.schools = schools;
        this.pupils = pupils;
        this.buses = buses;
    }

    public void distanceBetweenPupils() {

        for (Pupil pupil : pupils) {
            pupil.setDistanceToSchool(countDistanceToSchool(pupil));
            distanceBetweenPupils.add(countDistanceForOnePupilToOthers(pupil));
        }
    }

    public void distanceBetweenBusesAndPupils() {

        for (Bus bus : buses) {
            distanceBetweenBusesAndPupils.add(countDistanceBusPupils(bus));
        }
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

    private Integer countDistanceToSchool(Pupil pupil) {
        return countDistanceBetweenCoords(pupil.getCoords(), findSchool(pupil).getCoords());
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

    public void printDistances() {

        int nrPupil = 0;

        for (List<Integer> list : distanceBetweenPupils) {
            nrPupil++;
            System.out.println("Pupil:" + nrPupil + "; distance to school = " + pupils.get(nrPupil - 1).getDistanceToSchool());

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
}
