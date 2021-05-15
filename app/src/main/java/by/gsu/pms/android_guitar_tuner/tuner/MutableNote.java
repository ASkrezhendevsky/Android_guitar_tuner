package by.gsu.pms.android_guitar_tuner.tuner;

import java.util.Objects;

public class MutableNote {
    private String name;
    private double frequency;
    private float relativeDifference;

    public MutableNote() {
        // Default constructor
    }

    MutableNote(final String name, final double frequency, final float relativeDifference) {
        this.name = name;
        this.frequency = frequency;
        this.relativeDifference = relativeDifference;
    }

    public String getName() {
        return name;
    }

    public double getFrequency() {
        return frequency;
    }

    public float getRelativeDifference() {
        return relativeDifference;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setFrequency(final double frequency) {
        this.frequency = frequency;
    }

    public void setPercentOffset(final float percentOffset) {
        this.relativeDifference = percentOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableNote note = (MutableNote) o;
        return Double.compare(note.frequency, frequency) == 0 &&
                Float.compare(note.relativeDifference, relativeDifference) == 0 &&
                name.equals(note.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        final int MODIFIER = 31;

        result = MODIFIER * result + name.hashCode();
        result = MODIFIER * result + Float.floatToIntBits(relativeDifference);

        long longFrequency = MODIFIER*Double.doubleToLongBits(frequency);

        result = MODIFIER * result + (int) (longFrequency ^ (longFrequency >>> 32));

        return result;
    }
}
