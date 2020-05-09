package distance;

import model.Pupil;

public class BusDistance extends Distance {

    private Pupil pupil;

    public BusDistance(Pupil pupil, Integer distance) {
        this.pupil = pupil;
        this.distance = distance;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    @Override
    public String toString() {
        return "BusDistance{" +
                "pupil=" + pupil +
                ", distance=" + distance +
                '}';
    }
}
