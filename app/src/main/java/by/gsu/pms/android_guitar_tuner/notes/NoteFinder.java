package by.gsu.pms.android_guitar_tuner.notes;

import java.util.Locale;

public class NoteFinder {

    private final static double NEXT_NOTE_MODIFIER = Math.pow(2, 1 / 12.0);

    private final double[] noteFrequencies = new double[]{
            16.3516, 17.3239, 18.354, 19.4454, 20.6017, 21.8268, 23.1247, 24.4997, 25.9565, 27.5, 29.1352, 30.8677,
            32.7032, 34.6478, 36.7081, 38.8909, 41.2034, 43.6535, 46.2493, 48.9994, 51.9131, 55.0, 58.2705, 61.7354,
            65.4064, 69.2957, 73.4162, 77.7817, 82.4069, 87.3071, 92.4986, 97.9989, 103.826, 110.0, 116.541, 123.471,
            130.813, 138.591, 146.832, 155.563, 164.814, 174.614, 184.997, 195.998, 207.652, 220.0, 233.082, 246.942,
            261.626, 277.183, 293.665, 311.127, 329.628, 349.228, 369.994, 391.995, 415.305, 440.0, 466.164, 493.883,
            523.251, 554.365, 587.33, 622.254, 659.255, 698.456, 739.989, 783.991, 830.609, 880.0, 932.328, 987.767,
            1046.5, 1108.73, 1174.66, 1244.51, 1318.51, 1396.91, 1479.98, 1567.98, 1661.22, 1760.0, 1864.66, 1975.53,
            2093.0, 2217.46, 2349.32, 2489.02, 2637.02, 2793.83, 2959.96, 3135.96, 3322.44, 3520.0, 3729.31, 3951.07,
            4186.01, 4434.92, 4698.64, 4978.03, 5274.04, 5587.65, 5919.9, 6271.9, 6644.9, 7040.0, 7458.6, 7902.1
    };

    private final String[] noteNames = new String[]{"C", "C♯", "D", "D♯", "E", "F", "F♯", "G", "G♯", "A", "A♯", "B"};

    private String noteName;
    private float relativeDifference;

    public NoteFinder() {

    }

    public void setFrequency(final double frequency) {
        int length = noteFrequencies.length;
        int frequencyIndex = 0;

        for (int upper = 1, lower = 0; upper < length && lower < length; upper++, lower++) {
            if (frequency < noteFrequencies[upper]) {
                double deviationFormLower = frequency - noteFrequencies[lower];
                double deviationFormUpper = noteFrequencies[upper] - frequency;

                frequencyIndex = deviationFormLower < deviationFormUpper ? lower : upper;
                break;
            }
        }

        noteName = String.format(Locale.ENGLISH, "%s%d", noteNames[frequencyIndex % noteNames.length], frequencyIndex/noteNames.length);
        //noteName = noteNames[frequencyIndex % noteNames.length];
        System.out.println(noteName + frequencyIndex);

        double difference = frequency - noteFrequencies[frequencyIndex];

        if (difference > 0) {
            relativeDifference = (float) (difference / (noteFrequencies[frequencyIndex] - getUpperFrequency(noteFrequencies[frequencyIndex])));
        } else {
            relativeDifference = (float) -(difference / (noteFrequencies[frequencyIndex] - getLowerFrequency(noteFrequencies[frequencyIndex])));
        }
    }

    public String getNoteName() {
        return noteName;
    }

    public float getRelativeDifference() {
        return relativeDifference;
    }

    private double getLowerFrequency(double frequency) {
        return frequency * NEXT_NOTE_MODIFIER;
    }

    private double getUpperFrequency(double frequency) {
        return frequency / NEXT_NOTE_MODIFIER;
    }
}
