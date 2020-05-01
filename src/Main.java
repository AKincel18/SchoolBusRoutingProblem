import datareader.DataReader;
import distance.Distance;
import model.Bus;
import model.Pupil;
import model.School;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DataReader dataReader = new DataReader();
        dataReader.readData();

        List<Bus> buses = dataReader.getBuses();
        List<Pupil> pupils = dataReader.getPupils();
        List<School> schools = dataReader.getSchools();

        Bus.printBuses(buses);
        Pupil.printPupils(pupils);
        School.printSchools(schools);

        Distance distance = new Distance(schools, pupils, buses);

        distance.distanceBetweenPupils();
        distance.distanceBetweenBusesAndPupils();
        distance.distanceBetweenSchoolsAndPupils();

        distance.printDistances();
        distance.printBusDistance();
        distance.printSchoolDistance();

    }
}