package by.gsu.pms.android_guitar_tuner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import by.gsu.pms.android_guitar_tuner.presenter.PitchPresenter;
import by.gsu.pms.android_guitar_tuner.tuner.Tuner;

public class MainActivity extends AppCompatActivity {
    private PitchPresenter pitchPresenter;
    private Button button;
    private boolean isListening = false;

    private int buttonStart;
    private int buttonStop;

    private final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();

        buttonStart = resources.getColor(R.color.purple_200);
        buttonStop = resources.getColor(R.color.purple_700);

        TextView noteText = (TextView) findViewById(R.id.noteName);
        TextView noteArrow = (TextView) findViewById(R.id.noteArrow);
        TextView noteFrequency = (TextView) findViewById(R.id.noteFrequency);
        button = (Button) findViewById(R.id.button);

        pitchPresenter = new PitchPresenter(new Tuner(), noteText, noteArrow, noteFrequency);

        button.setOnClickListener(v -> {
            if (hasPermissions()){
                switchListeningState();
            }
            else {
                requestPerms();
            }
        });
    }

    private void switchListeningState(){
        if(!isListening){
            System.out.println("Start Listening");
            pitchPresenter.startListeningForNotes();
            button.setText(getResources().getString(R.string.button_stop));
            button.setBackgroundColor(buttonStop);
        }
        else
        {
            System.out.println("Stop Listening");
            pitchPresenter.stopListeningForNotes();
            button.setText(getResources().getString(R.string.button_start));
            button.setBackgroundColor(buttonStart);
        }
        isListening = !isListening;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private boolean hasPermissions(){
        int res;
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                switchListeningState();
            }
        }
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }
}