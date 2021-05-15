package by.gsu.pms.android_guitar_tuner.recording;

import android.media.AudioRecord;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class RecorderAndroid6 extends AbstractRecorder{
    private final float[] floatBuffer;

    public RecorderAndroid6() {
        this.floatBuffer = new float[getReadSize()];
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public float[] readNext() {
        getAudioRecorder().read(floatBuffer, 0, getReadSize(), AudioRecord.READ_BLOCKING);
        return floatBuffer;
    }
}
