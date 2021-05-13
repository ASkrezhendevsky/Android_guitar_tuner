package by.gsu.pms.android_guitar_tuner.tuner;

import android.media.tv.TvContract;

import by.gsu.pms.android_guitar_tuner.recording.Recorder;
import by.gsu.pms.android_guitar_tuner.recording.RecordingConfig;
import io.reactivex.Observable;

public class Tuner {

    private final Observable<MutableNote> observable;

    private final PitchDetector detector;
    private final Recorder recorder;

    private final MutableNote mutableNote = new MutableNote(0);

    public Tuner() {
        detector = new YINPitchDetector(RecordingConfig.AUDIO_SAMPLE_RATE, RecordingConfig.AUDIO_RECORD_READ_SIZE);
        recorder = new Recorder();

        observable = Observable.create(emitter -> {
            try {
                recorder.startRecording();

                while (!emitter.isDisposed()) {
                   // mutableNote.setFrequency(1detector.detect(recorder.readNext())
                    mutableNote.setFrequency(1);
                    emitter.onNext(mutableNote);
                }

                recorder.stopRecording();

                emitter.onComplete();
            } catch (Exception exception) {
                emitter.tryOnError(exception);
            }
        });
    }

    public Observable<MutableNote> startListening() {
        return observable.share();
    }
}
