package by.gsu.pms.android_guitar_tuner.recording;

import android.media.AudioRecord;
import android.os.Build;

public class Recorder {
    private static final short SHORT_DIVISOR = (short) (-1 * Short.MIN_VALUE);

    private AudioRecord audioRecorder;
    private int readSize;
    private short[] buffer;
    private float[] floatBuffer;

    public Recorder() {
        /*this.audioRecorder = new AudioRecord(RecordingConfig.AUDIO_RECORD_AUDIO_SOURCE, RecordingConfig.AUDIO_SAMPLE_RATE,
                RecordingConfig.AUDIO_RECORD_CHANNEL_CONFIG, RecordingConfig.AUDIO_RECORD_AUDIO_FORMAT, RecordingConfig.AUDIO_RECORD_BUFFER_SIZE);
        this.readSize = RecordingConfig.AUDIO_RECORD_READ_SIZE;
        this.buffer = new short[readSize];
        this.floatBuffer = new float[readSize];*/
    }

    public void startRecording() {
        //audioRecorder.startRecording();
    }

    public void stopRecording() {
        //audioRecorder.stop();
    }

    /*public float[] readNext() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioRecorder.read(floatBuffer, 0, readSize, AudioRecord.READ_BLOCKING);
        } else {
            audioRecorder.read(buffer, 0, readSize);

            convert(buffer, floatBuffer);
        }

        return floatBuffer;
    }*/

    private static void convert(final short[] array, final float[] convertedArray) {
        int arrayLength = array.length;
        int convertedArrayLength = convertedArray.length;

        for (int i = 0; i < arrayLength && i < convertedArrayLength; i++) {
            convertedArray[i] = ((float) array[i]) / SHORT_DIVISOR;
            convertedArray[i] = convertedArray[i] < -1 ? -1 : Math.min(convertedArray[i], 1);
        }
    }
}
