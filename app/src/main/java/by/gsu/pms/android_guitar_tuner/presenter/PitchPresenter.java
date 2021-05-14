package by.gsu.pms.android_guitar_tuner.presenter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import by.gsu.pms.android_guitar_tuner.tuner.Tuner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PitchPresenter {
    private final Tuner tuner;
    private final TextView noteName;
    private final TextView noteArrow;

    private Disposable disposable;

    public PitchPresenter(Tuner tuner, TextView noteName, TextView noteArrow) {
        this.tuner = tuner;
        this.noteName = noteName;
        this.noteArrow = noteArrow;
    }

    public void startListeningForNotes() {
        disposable = tuner.startListening()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        note -> {
                            noteName.setText(note.getName());

                            noteArrow.setX(noteName.getX() + note.getPercentOffset() * noteName.getX());
                        },
                        error -> System.out.println("error")
                );
    }

    public void stopListeningForNotes() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
