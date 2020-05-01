package model;

import java.util.List;

public class School {

    private Coords coords;

    private int id;

    public School() {
    }

    public School(int x, int y, int id) {
        this.coords = new Coords(x, y);
        this.id = id;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void printSchools(List<School> schools) {

        for (School school : schools) {
            System.out.println(school.toString());
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "model.School{" +
                "coords=" + coords +
                ", id=" + id +
                '}';
    }
}
