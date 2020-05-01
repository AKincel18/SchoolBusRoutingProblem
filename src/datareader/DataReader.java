package datareader;

import model.Bus;
import model.Pupil;
import model.School;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constants.Constants.*;

public class DataReader {


    private List<Bus> buses = new ArrayList<>();

    private List<Pupil> pupils = new ArrayList<>();

    private List<School> schools = new ArrayList<>();

    public DataReader() {
        readData();
    }

    private void readData() {

        try {

            List<String> files = new ArrayList<>(Arrays.asList(nameBusFile, namePupilsFile, nameSchoolsFile));

            for (String file : files) {

                List<String> strings = Files.readAllLines(Paths.get(mainPath + file));
                strings.remove(0); //remove header line

                for (String line : strings) {
                    String[] split = line.split(" ");

                    int x = Integer.valueOf(split[0]);
                    int y = Integer.valueOf(split[1]);
                    int extraVar = Integer.valueOf(split[2]); //different variable depend on file

                    switch (file) {
                        case nameBusFile:
                            buses.add(new Bus(x, y, extraVar));
                            break;
                        case namePupilsFile:
                            pupils.add(new Pupil(x, y, extraVar));
                            break;
                        case nameSchoolsFile:
                            schools.add(new School(x, y, extraVar));
                            break;
                    }

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Pupil> getPupils() {
        return pupils;
    }

    public List<School> getSchools() {
        return schools;
    }
}
