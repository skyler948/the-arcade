package time;

import static com.raylib.Raylib.*;

public class Timer {

    private double waitTime;

    private double startTime;
    private boolean playing = false;

    public Timer(double waitTime) {
        this.waitTime = waitTime;
    }

    public void start() {
        if (playing) return;
        playing = true;
        startTime = GetTime();
    }

    public void reset(boolean start) {
        if (!playing) return;
        playing = false;
        startTime = 0.0;

        if (start) start();
    }

    public boolean isDone() {
        if (GetTime() - startTime >= waitTime && playing) {
            playing = false;
            return true;
        }

        return false;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }

    public double getElapsed() {
        isDone();
        return GetTime() - startTime;
    }

    public boolean isPlaying() {
        return playing;
    }

}
