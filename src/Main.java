import datareader.DataReader;
import inputs.Bus;
import inputs.Pupil;
import inputs.School;

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


    }
}
