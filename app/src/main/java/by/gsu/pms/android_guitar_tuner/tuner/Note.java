package by.gsu.pms.android_guitar_tuner.tuner;

public class Note {
    private String name;
    private double frequency;
    private float relativeDifference;

    public Note() {
        // Default constructor
    }

    public Note(final String name, final double frequency, final float relativeDifference) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
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
