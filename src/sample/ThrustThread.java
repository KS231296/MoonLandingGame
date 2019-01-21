package sample;

import java.beans.PropertyChangeSupport;

public class ThrustThread implements Runnable {
    private boolean isRunning;
    private int interval;
    private double thrust;
    private double thrustUpdated;
    private PropertyChangeSupport support;

    public double getThrust() {
        return thrust;
    }

    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

    public double getThrustUpdated() {
        return thrustUpdated;
    }

    public void setThrustUpdated(double thrustUpdated) {
        this.thrustUpdated = thrustUpdated;
    }

    public ThrustThread(int interval) {
        this.interval = interval;
    }



    @Override
    public void run() {
        isRunning = true;
        while (isRunning){

            try {

                thrustUpdated = thrust;


                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
