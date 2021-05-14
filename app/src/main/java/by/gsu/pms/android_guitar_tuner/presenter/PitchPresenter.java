package by.gsu.pms.android_guitar_tuner.presenter;

import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import by.gsu.pms.android_guitar_tuner.tuner.Tuner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PitchPresenter {
    private final Tuner tuner;
    private final TextView noteName;
    private final View noteArrow;
    private final TextView noteFrequency;

    private Disposable disposable;

    public PitchPresenter(Tuner tuner, TextView noteName, View noteArrow, TextView noteFrequency) {
        this.tuner = tuner;
        this.noteName = noteName;
        this.noteArrow = noteArrow;
        this.noteFrequency = noteFrequency;
    }

    public void startListeningForNotes() {
        if (disposable == null || disposable.isDisposed()) {
            disposable = tuner.startListening()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            note -> {
                                noteName.setText(note.getName());
                                noteFrequency.setText(String.format(Locale.ENGLISH, "%.2f Hz", note.getFrequency()));
                                noteArrow.setX(noteName.getX() + note.getPercentOffset() * noteName.getX());
                            },
                            error -> System.out.println("error")
                    );
        }
    }

    public void stopListeningForNotes() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
