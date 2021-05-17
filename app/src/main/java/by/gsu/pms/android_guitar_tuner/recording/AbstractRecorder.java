package by.gsu.pms.android_guitar_tuner.recording;

import android.media.AudioRecord;

public abstract class AbstractRecorder {
    private final AudioRecord audioRecorder;
    private final int readSize;

    public AbstractRecorder() {
        this.audioRecorder = new AudioRecord(RecordingConfig.AUDIO_RECORD_AUDIO_SOURCE, RecordingConfig.AUDIO_SAMPLE_RATE,
                RecordingConfig.AUDIO_RECORD_CHANNEL_CONFIG, RecordingConfig.AUDIO_RECORD_AUDIO_FORMAT, RecordingConfig.AUDIO_RECORD_BUFFER_SIZE);
        this.readSize = RecordingConfig.AUDIO_RECORD_READ_SIZE;
    }

    protected int getReadSize() {
        return readSize;
    }

    protected AudioRecord getAudioRecorder() {
        return audioRecorder;
    }

    public void startRecording() {
        audioRecorder.startRecording();
    }

    public void stopRecording() {
        audioRecorder.stop();
    }

    abstract public float[] readNext();
}
