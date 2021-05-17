package by.gsu.pms.android_guitar_tuner.recording;

public class AndroidLower6Recorder extends AbstractRecorder{
    private static final short SHORT_DIVISOR = (short) (-1 * Short.MIN_VALUE);
    private final float[] floatBuffer;
    private final short[] buffer;

    public AndroidLower6Recorder() {
        this.floatBuffer = new float[getReadSize()];
        this.buffer = new short[getReadSize()];
    }

    @Override
    public float[] readNext() {
        getAudioRecorder().read(buffer, 0, getReadSize());

        for (int i = 0; i < buffer.length && i <  floatBuffer.length; i++) {
            floatBuffer[i] = ((float) buffer[i]) / SHORT_DIVISOR;
            floatBuffer[i] = floatBuffer[i] < -1 ? -1 : Math.min(floatBuffer[i], 1);
        }
        return floatBuffer;
    }
}
