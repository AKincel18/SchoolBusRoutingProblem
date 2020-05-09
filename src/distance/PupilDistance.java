package distance;

import model.Pupil;

public class PupilDistance extends Distance {

    private Pupil pupil;

    public PupilDistance(Pupil pupil, Integer distance) {
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
        return "PupilDistance{" +
                "pupil=" + pupil +
                ", distance=" + distance +
                '}';
    }
}
