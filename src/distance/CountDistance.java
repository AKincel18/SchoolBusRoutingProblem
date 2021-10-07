package distance;

import model.Bus;
import model.Coords;
import model.Pupil;
import model.School;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountDistance {

    private final Map<Pupil, List<PupilDistance>> distanceBetweenPupils = new HashMap<>();

    private final Map<Bus, List<BusDistance>> distanceBetweenBusesAndPupils = new HashMap<>();

    private final Map<School, List<SchoolDistance>> distanceBetweenSchoolsAndPupils = new HashMap<>();

    private final List<School> schools;

    private final List<Pupil> pupils;

    private final List<Bus> buses;

    public CountDistance(List<School> schools, List<Pupil> pupils, List<Bus> buses) {
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
            distanceBetweenPupils.put(pupil, countDistanceForOnePupilToOthers(pupil));
        }
    }

    private void distanceBetweenBusesAndPupils() {

        for (Bus bus : buses) {
            distanceBetweenBusesAndPupils.put(bus, countDistanceBusPupils(bus));
        }
        //sortedBuses();
    }

/*    private void sortedBuses() {
        distanceBetweenBusesAndPupils = distanceBetweenBusesAndPupils.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Bus::getCapacity)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }*/

    private void distanceBetweenSchoolsAndPupils() {

        for (School school : schools) {
            distanceBetweenSchoolsAndPupils.put(school, countDistanceSchoolPupils(school));
        }
        //sortedSchools();
    }

/*    private void sortedSchools() {
        distanceBetweenSchoolsAndPupils = distanceBetweenSchoolsAndPupils.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(List::size)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }*/

    private List<SchoolDistance> countDistanceSchoolPupils(School school){

        List<SchoolDistance> distance = new ArrayList<>();

        for (Pupil pupil : pupils) {
            if (pupil.getSchoolId() == school.getId()) {
                distance.add(new SchoolDistance(pupil, countDistanceBetweenCoords(pupil.getCoords(), school.getCoords())));
            }
        }

        return distance;
    }

    private List<BusDistance> countDistanceBusPupils(Bus bus) {

        List<BusDistance> distance = new ArrayList<>();
        for (Pupil pupil : pupils) {
            distance.add(new BusDistance(pupil, countDistanceBetweenCoords(pupil.getCoords(), bus.getCoords())));
        }
        return distance;
    }


    private List<PupilDistance> countDistanceForOnePupilToOthers(Pupil pupil) {

        School pupilSchool = findSchool(pupil);
        List<PupilDistance> distances = new ArrayList<>();
        for (Pupil p : pupils) {
            if (pupil.getCoords() != p.getCoords()){
                if (pupilSchool.getId() == p.getSchoolId()) {
                    distances.add(new PupilDistance(p, countDistanceBetweenCoords(pupil.getCoords(), p.getCoords())));
                }
/*                else {
                    distances.add(-1);
                }*/
            }
/*            else {
                distances.add(-1);
            }*/
        }

        return distances;


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

    public Map<Pupil, List<PupilDistance>> getDistanceBetweenPupils() {
        return distanceBetweenPupils;
    }

    public Map<Bus, List<BusDistance>> getDistanceBetweenBusesAndPupils() {
        return distanceBetweenBusesAndPupils;
    }

    public Map<School, List<SchoolDistance>> getDistanceBetweenSchoolsAndPupils() {
        return distanceBetweenSchoolsAndPupils;
    }

    public void printDistances() {

        for(Map.Entry<Pupil, List<PupilDistance>> entry : distanceBetweenPupils.entrySet()) {
            System.out.println(entry.getKey());
            for (PupilDistance distance : entry.getValue()) {
                System.out.println(distance + " ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public void printBusDistance() {

        for(Map.Entry<Bus, List<BusDistance>> entry : distanceBetweenBusesAndPupils.entrySet()) {
            System.out.println(entry.getKey());
            for (BusDistance distance : entry.getValue()) {
                System.out.println(distance + " ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public void printSchoolDistance() {

        for(Map.Entry<School, List<SchoolDistance>> entry : distanceBetweenSchoolsAndPupils.entrySet()) {
            System.out.println(entry.getKey());
            for (SchoolDistance distance : entry.getValue()) {
                System.out.println(distance + " ");
            }
            System.out.println();
            System.out.println();
        }

    }
}
