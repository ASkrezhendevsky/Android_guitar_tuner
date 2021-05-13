package by.gsu.pms.android_guitar_tuner.tuner;

import android.media.tv.TvContract;

import by.gsu.pms.android_guitar_tuner.notes.NoteFinder;
import by.gsu.pms.android_guitar_tuner.recording.Recorder;
import by.gsu.pms.android_guitar_tuner.recording.RecordingConfig;
import io.reactivex.Observable;

public class Tuner {

    private final Observable<MutableNote> observable;

    private final PitchDetector detector;
    private final Recorder recorder;

    private final MutableNote mutableNote = new MutableNote();
    private final NoteFinder finder = new NoteFinder();

    public Tuner() {
        detector = new YINPitchDetector(RecordingConfig.AUDIO_SAMPLE_RATE, RecordingConfig.AUDIO_RECORD_READ_SIZE);
        recorder = new Recorder();

        observable = Observable.create(emitter -> {
            try {
                recorder.startRecording();

                while (!emitter.isDisposed()) {
                    double frequency = detector.detect(recorder.readNext());
                    finder.setFrequency(frequency);
                    synchronized (mutableNote) {
                        mutableNote.setFrequency(frequency);
                        mutableNote.setName(finder.getNoteName());
                        mutableNote.setPercentOffset(finder.getPercentageDifference());
                    }
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
