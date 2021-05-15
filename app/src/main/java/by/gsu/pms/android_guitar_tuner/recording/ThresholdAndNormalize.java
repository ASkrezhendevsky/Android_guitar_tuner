package by.gsu.pms.android_guitar_tuner.recording;

public class ThresholdAndNormalize implements WaveFilter {
    double value = 0;

    public ThresholdAndNormalize(){

    }

    public ThresholdAndNormalize(double value){
        this.value = value;
    }

    @Override
    public float[] process(float[] waveData){
        float maxAmplitude = 0;
        for (int i = 0; i < waveData.length; i++) {
            if(maxAmplitude < waveData[i]){
                maxAmplitude = waveData[i];
            }
        }

        if(maxAmplitude < value){
            return new float[0];
        }

        for (int i = 0; i < waveData.length; i++) {
            waveData[i] = waveData[i]/maxAmplitude;
        }

        return waveData;
    }
}
