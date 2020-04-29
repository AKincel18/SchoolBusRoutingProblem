package inputs;

import java.util.List;

public class Bus {

    private Coords coords;

    private int capacity;

    public Bus(Coords coords, int capacity) {
        this.coords = coords;
        this.capacity = capacity;
    }

    public Bus(int x, int y, int capacity) {
        this.coords = new Coords(x, y);
        this.capacity = capacity;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static void printBuses(List<Bus> buses) {

        for (Bus bus : buses) {
            System.out.println(bus.toString());
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "inputs.Bus{" +
                "coords=" + coords +
                ", capacity=" + capacity +
                '}';
    }
}
