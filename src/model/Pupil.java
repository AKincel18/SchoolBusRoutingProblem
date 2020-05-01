package model;

import java.util.List;

public class Pupil {

    private Coords coords;

    private int schoolId;


    public Pupil(int x, int y, int schoolId) {
        this.coords = new Coords(x, y);
        this.schoolId = schoolId;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public static void printPupils(List<Pupil> pupils) {

        for (Pupil pupil : pupils) {
            System.out.println(pupil.toString());
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "model.Pupil{" +
                "coords=" + coords +
                ", schoolId=" + schoolId +
                '}';
    }
}
