package by.gsu.pms.android_guitar_tuner.tuner;

public class MutableNote {
    private float frequency;

    public MutableNote(float frequency) {
        this.frequency = frequency;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "MutableNote{" +
                "frequency=" + frequency +
                '}';
    }
}
