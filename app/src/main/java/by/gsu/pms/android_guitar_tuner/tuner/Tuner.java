package by.gsu.pms.android_guitar_tuner.tuner;

import by.gsu.pms.android_guitar_tuner.notes.NoteFinder;
import by.gsu.pms.android_guitar_tuner.recording.Recorder;
import by.gsu.pms.android_guitar_tuner.recording.RecordingConfig;
import by.gsu.pms.android_guitar_tuner.recording.ThresholdAndNormalize;
import by.gsu.pms.android_guitar_tuner.recording.WaveFilter;
import io.reactivex.Observable;

public class Tuner {

    private final Observable<Note> observable;

    private final PitchDetector detector;
    private final Recorder recorder;

    private final NoteFinder finder = new NoteFinder();

    public Tuner() {
        detector = new YINPitchDetector(RecordingConfig.AUDIO_SAMPLE_RATE, RecordingConfig.AUDIO_RECORD_READ_SIZE);
        recorder = new Recorder();

        observable = Observable.create(emitter -> {
            try {
                recorder.startRecording();

                WaveFilter filter = new ThresholdAndNormalize(1e-2);

                while (!emitter.isDisposed()) {
                    float[] wave = recorder.readNext();

                    wave = filter.process(wave);

                    Note note;

                    if (wave.length == 0) {
                        emitter.onNext(new Note("-", 0, 0));
                    } else {
                        emitter.onNext(finder.getNote(detector.detect(wave)));
                    }
                }

                recorder.stopRecording();

                emitter.onComplete();
            } catch (Exception exception) {
                emitter.tryOnError(exception);
            }
        });
    }

    public Observable<Note> startListening() {
        return observable.share();
    }
}
