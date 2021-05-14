package by.gsu.pms.android_guitar_tuner.tuner;

import by.gsu.pms.android_guitar_tuner.notes.NoteFinder;
import by.gsu.pms.android_guitar_tuner.recording.Recorder;
import by.gsu.pms.android_guitar_tuner.recording.RecordingConfig;
import by.gsu.pms.android_guitar_tuner.recording.ThresholdAndNormalize;
import by.gsu.pms.android_guitar_tuner.recording.WaveFilter;
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

                WaveFilter filter = new ThresholdAndNormalize(1e-2);

                String name;
                double frequency;
                float percentOffset;

                while (!emitter.isDisposed()) {
                    float[] wave = recorder.readNext();

                    wave = filter.process(wave);

                    if(wave.length == 0){
                        name = "-" ;
                        frequency = 0;
                        percentOffset = 0;
                    }
                    else{
                        frequency = detector.detect(wave);
                        finder.setFrequency(frequency);
                        name = finder.getNoteName();
                        percentOffset = finder.getRelativeDifference();
                    }

                    synchronized (mutableNote) {
                        mutableNote.setFrequency(frequency);
                        mutableNote.setName(name);
                        mutableNote.setPercentOffset(percentOffset);
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
