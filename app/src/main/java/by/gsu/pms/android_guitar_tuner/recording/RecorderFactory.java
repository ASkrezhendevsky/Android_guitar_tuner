package by.gsu.pms.android_guitar_tuner.recording;

import android.os.Build;

public class RecorderFactory {
    public static AbstractRecorder getRecorder(int sdk){
        if(sdk >= Build.VERSION_CODES.M)
        {
            return new Android6Recorder();
        }
        else {
            return new AndroidLower6Recorder();
        }
    }
}
