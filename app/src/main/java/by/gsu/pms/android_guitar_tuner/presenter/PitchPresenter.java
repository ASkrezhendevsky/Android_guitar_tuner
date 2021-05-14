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
    private Tuner tuner;
    private TextView noteName;
    private TextView noteArrow;

    private int center = 0;

    private Disposable disposable;

    public PitchPresenter(Tuner tuner, TextView noteName, TextView noteArrow, int center) {
        this.tuner = tuner;
        this.noteName = noteName;
        this.noteArrow = noteArrow;
        this.center = center;
    }

    public void startListeningForNotes() {
        disposable = tuner.startListening()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        note -> {
                            noteName.setText(note.getName());
                            noteArrow.setX(center + note.getPercentOffset() * 10);

                            System.out.println(note.getPercentOffset());
                            System.out.println(note.getName());
                        },
                        error -> {
                            System.out.println("error");
                        }
                );
    }

    public void stopListeningForNotes() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
