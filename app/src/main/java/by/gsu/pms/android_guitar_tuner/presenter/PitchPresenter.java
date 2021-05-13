package by.gsu.pms.android_guitar_tuner.presenter;

import android.widget.TextView;

import by.gsu.pms.android_guitar_tuner.tuner.Tuner;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PitchPresenter {
    private Tuner tuner;
    private TextView view;

    private Disposable disposable;

    public PitchPresenter(Tuner tuner, TextView view) {
        this.tuner = tuner;
        this.view = view;
    }

    public void startListeningForNotes() {
        disposable = tuner.startListening()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(i -> {view.setText(i.toString()); System.out.println("set i; i = "+i);},
                        error -> {System.out.println("error");});
    }

    public void stopListeningForNotes() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
