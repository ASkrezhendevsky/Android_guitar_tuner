package by.gsu.pms.android_guitar_tuner.recording;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;

public class RecordingConfig {
    public static final int AUDIO_SAMPLE_RATE = 44100;

    public static final int AUDIO_RECORD_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_DEFAULT;
    public static final int AUDIO_RECORD_AUDIO_FORMAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? AudioFormat.ENCODING_PCM_FLOAT : AudioFormat.ENCODING_PCM_16BIT;
    public static final int AUDIO_RECORD_BUFFER_SIZE = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,
            AUDIO_RECORD_CHANNEL_CONFIG, AUDIO_RECORD_AUDIO_FORMAT);
    public static final int AUDIO_RECORD_READ_SIZE = AUDIO_RECORD_BUFFER_SIZE / 4;
    public static final int AUDIO_RECORD_AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT;
}
