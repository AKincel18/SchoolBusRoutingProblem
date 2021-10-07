package distance;

import model.Pupil;

public class SchoolDistance extends Distance {

    private Pupil pupil;

    public SchoolDistance(Pupil pupil, Integer distance) {
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
        return "SchoolDistance{" +
                "pupil=" + pupil +
                ", distance=" + distance +
                '}';
    }
}
