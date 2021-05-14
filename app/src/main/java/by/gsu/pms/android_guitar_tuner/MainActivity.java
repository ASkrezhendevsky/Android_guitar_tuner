package by.gsu.pms.android_guitar_tuner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import by.gsu.pms.android_guitar_tuner.presenter.PitchPresenter;
import by.gsu.pms.android_guitar_tuner.tuner.Tuner;

public class MainActivity extends AppCompatActivity {

    private TextView noteText;
    private TextView noteArrow;
    private PitchPresenter pitchPresenter;
    private Button button;
    private boolean isListening = false;

    private final int PERMISSION_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteText = (TextView) findViewById(R.id.noteName);
        noteArrow = (TextView) findViewById(R.id.noteArrow);
        Button button = (Button) findViewById(R.id.button);

        pitchPresenter = new PitchPresenter(new Tuner(), noteText, noteArrow, getWindowManager().getCurrentWindowMetrics().getBounds().centerX());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermissions()){
                    switchListeningState();
                }
                else {
                    requestPerms();
                }
            }
        });
    }

    private void switchListeningState(){
        if(!isListening){
            System.out.println("Start Listening");
            pitchPresenter.startListeningForNotes();
            System.out.println("Start 222");
        }
        else
        {
            System.out.println("Stop Listening");
            pitchPresenter.stopListeningForNotes();
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
        int res = 0;
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
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    switchListeningState();
                } else {
                    // permission denied
                }
                return;
        }
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }
}