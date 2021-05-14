package by.gsu.pms.android_guitar_tuner.tuner;

public class YINPitchDetector implements PitchDetector {
    private static final float ABSOLUTE_THRESHOLD = 0.125f;

    private final double sampleRate;
    private final double[] resultBuffer;

    public YINPitchDetector(double sampleRate, int resultBufferSize){
        this.sampleRate = sampleRate;
        this.resultBuffer = new double[resultBufferSize / 2];
    }

    @Override
    public double detect(float[] wave) {
        int tau;

        autoCorrelationDifference(wave);

        cumulativeMeanNormalizedDifference();

        tau = absoluteThreshold();

        float betterTau = parabolicInterpolation(tau);

        return sampleRate / betterTau;
    }

    private void autoCorrelationDifference(final float[] wave) {
        int length = resultBuffer.length;

        for (int tau = 1; tau < length; tau++) {
            for (int i = 0; i < length; i++) {
                resultBuffer[tau] += Math.pow((wave[i] - wave[i + tau]), 2);
            }
        }
    }

    private void cumulativeMeanNormalizedDifference() {
        int length = resultBuffer.length;
        float runningSum = 0;

        resultBuffer[0] = 1;

        for (int tau = 1; tau < length; tau++) {
            runningSum += resultBuffer[tau];

            resultBuffer[tau] *= tau / runningSum;
        }
    }

    private int absoluteThreshold() {
        int tau;
        int length = resultBuffer.length;

        for (tau = 2; tau < length; tau++) {
            if (resultBuffer[tau] < ABSOLUTE_THRESHOLD) {
                while (tau + 1 < length && resultBuffer[tau + 1] < resultBuffer[tau]) {
                    tau++;
                }
                break;
            }
        }

        tau = tau >= length ? length - 1 : tau;

        return tau;
    }

    private float parabolicInterpolation(final int currentTau) {
        int x0 = currentTau < 1 ? currentTau : currentTau - 1;
        int x2 = currentTau + 1 < resultBuffer.length ? currentTau + 1 : currentTau;

        float betterTau;

        if (x0 == currentTau) {
            if (resultBuffer[currentTau] <= resultBuffer[x2]) {
                betterTau = currentTau;
            } else {
                betterTau = x2;
            }
        } else if (x2 == currentTau) {
            if (resultBuffer[currentTau] <= resultBuffer[x0]) {
                betterTau = currentTau;
            } else {
                betterTau = x0;
            }
        } else {
            double s0 = resultBuffer[x0];
            double s1 = resultBuffer[currentTau];
            double s2 = resultBuffer[x2];

            betterTau = (float) (currentTau + (s2 - s0) / (2 * (2 * s1 - s2 - s0)));
        }

        return betterTau;
    }
}
