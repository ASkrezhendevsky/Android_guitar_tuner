package by.gsu.pms.android_guitar_tuner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import by.gsu.pms.android_guitar_tuner.presenter.PitchPresenter;
import by.gsu.pms.android_guitar_tuner.tuner.Tuner;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private TextView noteText;
    private PitchPresenter pitchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteText = (TextView) findViewById(R.id.noteText2);
        pitchPresenter = new PitchPresenter(new Tuner(), noteText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pitchPresenter.startListeningForNotes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pitchPresenter.stopListeningForNotes();
    }
}